#20514470 Yuyang Zhang
from pathlib import Path
from flask import Flask, request, redirect, render_template, url_for, flash, abort
from helper import *
import sqlite3
import csv
from pprint import pprint

app = Flask(__name__)


####################
# Routes
####################

@app.route('/', methods=['GET'])
def index():
    return render_template('index.html')


@app.route('/statistics/', methods=['GET'])
def statistics():
    genres = get_genres()
    return render_template('statistics.html', genres=genres)


@app.route('/statistics/<genre_id>', methods=['GET'])
def statistics_genre(genre_id):
    genres = get_genres()
    stats = get_genre_statistics(genre_id)
    if not stats:
        return redirect(url_for('statistics'))
    return render_template('statistics.html', genres=genres, selected_genre_id=genre_id, stats=stats)


@app.route('/statistics/', methods=['POST'])
def statistics_process():
    # Get the genre id from the request
    genre_id = request.form.get('genre')    
    return redirect(url_for('statistics_genre', genre_id=genre_id))


@app.route('/upload/', methods=['GET'])
def upload():
    return render_template('upload.html')


@app.route('/upload/', methods=['POST'])
def upload_process():
    # Get the file from the request
    file = request.files.get('file')

    # Validate the file
    if not file or file.filename == '':
        flash('No file selected. Please upload a file.', 'danger')
        return redirect(url_for('upload'))
    
    # Check file extension for .tsv
    if not file.filename.lower().endswith('.tsv'):
        flash('Invalid file. Please upload a valid .tsv file.', 'danger')
        return redirect(url_for('upload'))

     # Save the file
    try:
        uploads_dir = Path.cwd() / 'uploads'
        uploads_dir.mkdir(exist_ok=True)
        filename = 'Artist.tsv'
        tsv_file = uploads_dir / filename
        file.save(tsv_file)

        # Update the Artist table
        if update_artist_table(tsv_file):
            flash('Successfully updated Artist table.', 'success')
        else:
            flash('Error updating Artist table. Please check the file format.', 'danger')
    except Exception as e:
        flash('An error occurred while processing the file.', 'danger')

    return redirect(url_for('index'))


@app.route('/add/', methods=['GET'])
def add():
    tracks = get_tracks_with_no_genre()
    return render_template('add.html', tracks=tracks)


@app.route('/add/', methods=['POST'])
def add_process():
    genre_name = request.form.get('genre_name')   
    track_ids = request.form.getlist('tracks')    

    return add_genre_and_tracks(genre_name, track_ids)


@app.errorhandler(404)
def page_not_found(e):
    return render_template('error.html', messages=['404: Page not found.'])

####################
# Functions
####################

def add_genre_and_tracks(genre_name, track_ids):
    # TODO: Not implemented
    conn = sqlite3.connect('iMusic.db')
    conn.row_factory = sqlite3.Row
    cursor = conn.cursor()

    try:
        #Validate Genre name
        if len(genre_name) < 3 or len(genre_name) > 120:
            flash('Problem with the provided genre name.', 'warning')
            return redirect(url_for('add'))

        cursor.execute("SELECT * FROM Genre WHERE Name = ?", (genre_name,))
        existing_genre = cursor.fetchone()
        if existing_genre:
            flash('Problem with the provided genre name.', 'warning')
            return redirect(url_for('add'))

        # Insert the new genre 
        cursor.execute("INSERT INTO Genre (Name) VALUES (?)", (genre_name,))
        new_genre_id = cursor.lastrowid

        # Update the genre of track
        for track_id in track_ids:
            cursor.execute("UPDATE Track SET GenreId = ? WHERE TrackId = ? AND GenreId IS NULL", (new_genre_id, track_id))
        
        conn.commit()
        flash('Successfully added genre and updated tracks.', 'success')
        return redirect(url_for('index'))
    
    except exception as e:
        flash('An error occurred while processing your request.', 'warning')
    finally:
        conn.close()

def update_artist_table(tsv_file: Path):
    try:
        # Connect to the SQLite database
        conn = sqlite3.connect('iMusic.db')
        conn.row_factory = sqlite3.Row
        cursor = conn.cursor()

        # Fetch existing artists from the database
        cursor.execute("SELECT * FROM Artist")
        rows = cursor.fetchall()

        with open(tsv_file, newline='', encoding='utf-8') as tsvfile:
            tsvfile.seek(0)  # Move the file pointer to the beginning
            tsv_reader = csv.reader(tsvfile, delimiter='\t')
            next(tsv_reader)  # Skip header row

            # Use executemany for bulk insert/update
            artist_data = [(row[0], row[1]) for row in tsv_reader]
            cursor.executemany("INSERT OR REPLACE INTO Artist (ArtistId, Name) VALUES (?, ?)", artist_data)
        conn.commit()
        return True

    except sqlite3.Error as e:
        return False
    except Exception as e:
        return False
    finally:
        conn.close()

def get_genres():
    # TODO: Not implemented
    try:
        # Connect to the SQLite database
        conn = sqlite3.connect('iMusic.db')
        conn.row_factory = sqlite3.Row
        cursor = conn.cursor()

        cursor.execute("SELECT * FROM Genre ORDER BY Genre.Name ASC")
        genres = cursor.fetchall()
        genres.append({'GenreId':'all', 'Name':'All Genres'})
        return genres
    except Exception as e:
        return False
    finally:
        conn.close()
    
    
    

def get_genre_statistics(genre_id):
    # TODO: Not implemented
    # Connect to the SQLite database
    conn = sqlite3.connect('iMusic.db')
    conn.row_factory = sqlite3.Row
    cursor = conn.cursor()
    if genre_id == 'all':
        # Return the statistics as a list
        cursor.execute("""SELECT 
            AVG(Track.UnitPrice),2 AS 'Price', 
            COUNT(Track.TrackId) AS 'Tracks', 
            COUNT(DISTINCT Track.AlbumId) AS 'Albums', 
            COUNT(DISTINCT Artist.ArtistId) AS 'Artists', 
            SUM(Track.Milliseconds)/1000 AS 'Duration', 
            ROUND(SUM(Track.UnitPrice),2) AS 'TotalValue'
        FROM 
            Track 
        JOIN 
            Album ON Track.AlbumId = Album.AlbumId 
        JOIN 
            Artist ON Album.ArtistId = Artist.ArtistId """)
        stats = cursor.fetchone()
        conn.close()
        return stats       
    else:
        # Return the unquie statistics as a list
        cursor.execute("""SELECT
            AVG(Track.UnitPrice) AS 'Price', 
            COUNT(Track.TrackId) AS 'Tracks', 
            COUNT(DISTINCT Album.AlbumId) AS 'Albums', 
            COUNT(DISTINCT Artist.ArtistId) AS 'Artists', 
            SUM(Track.Milliseconds) / 1000 AS 'Duration', 
            ROUND(SUM(Track.UnitPrice), 2) AS 'TotalValue'
        FROM 
            Track 
        JOIN 
            Album ON Track.AlbumId = Album.AlbumId 
        JOIN 
            Artist ON Album.ArtistId = Artist.ArtistId 
        WHERE
            Track.GenreId = ?""",(genre_id,))

        stats = cursor.fetchone()
        conn.close()
        return stats


def get_tracks_with_no_genre():
    # TODO: Not implemented
    conn = sqlite3.connect('iMusic.db')
    conn.row_factory = sqlite3.Row
    cursor = conn.cursor()
    cursor.execute("SELECT TrackId, Name FROM Track WHERE GenreId IS NULL ORDER BY Name ASC")
    tracks = cursor.fetchall()
    conn.close()
    return tracks
    


####################
# Main
####################
def main():
    # We need to set the secret key to use flash - there's no need to change or worry about this
    app.secret_key = 'I love dbi'
    # Run the app in debug mode, listening on port 5000
    app.run(debug=True, port=5000)

# This is the entry point for the application
if __name__ == "__main__":
    main()
