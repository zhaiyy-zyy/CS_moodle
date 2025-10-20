BEGIN {
    # Initialization of variables
    sends = 0;                  # Counter for sent packets
    recvs = 0;                  # Counter for received packets
    totalrecv = 0;              # Total received packets
    totalsend = 0;              # Total sent packets
    droppedPackets = 0;         # Counter for dropped packets
    highest_packet_id = 0;      # Variable for highest packet ID observed
    sum = 0;                    # Sum of packet durations
    recvnum = 0;                # Number of received packets
    starttime = 0;              # Start time of the measurement interval
    stoptime = 0;               # Stop time of the measurement interval
    recvdSize = 0;              # Total size of received packets
    totaldroppedPackets = 0;    # Total number of dropped packets
}

{
    # Processing each line of input data
    event = $1;                 # Event type (s, r, d)
    time = $3;                  # Time of the event
    pn = $35#packet-name;       # Packet name
    Ii = $41;                   # Packet ID
    pkt_size = $37;             # Packet size
    level = $19;                # Packet level
    Ii = $41;                   # Packet interface ID

    # Handling send events
    if (event == "s" && pn == "tcp" && level == "AGT") {
        sends++;                # Increment send counter
        if (time < starttime) {
            starttime = time;  # Update start time if necessary
        }
        if (start_time[Ii] == 0) {
            start_time[Ii] = time;  # Record start time for the interface
        }
    }

    # Handling receive events
    if (event == "r" && pn == "tcp" && level == "AGT") {
        recvs++;                # Increment receive counter
        stoptime = time;        # Update stop time
        recvdSize += pkt_size - pkt_size % 512;  # Update total received size
        end_time[Ii] = time;    # Record end time for the interface
    }

    # Handling dropped packets
    if (event == "d" && pn == "tcp" && pkt_size >= 512) {
        droppedPackets++;       # Increment dropped packet counter
    }

    # Update after every 20 seconds
    if ((stoptime - starttime) > 20) {
        sum = 0;                # Reset sum of packet durations
        recvnum = 0;            # Reset number of received packets
        # Calculate delay for each packet
        for (i in start_time) {
            start = start_time[i];
            end = end_time[i];
            packet_duration = end - start;
            if (packet_duration > 0) {
                sum += packet_duration;
                recvnum++;
            }
        }
        {
            delay = sum / recvnum;  # Calculate average delay
        }
        # Print statistics
        print(stoptime, sends, delay * 1000, droppedPackets, (recvdSize / (stoptime - starttime)) * (8 / 1000));
        starttime = stoptime;   # Update start time
        totalsend += sends;     # Update total sent packets
        totalrecv += recvs;     # Update total received packets
        totaldroppedPackets += droppedPackets;  # Update total dropped packets
        sends = 0;              # Reset send counter
        recvs = 0;              # Reset receive counter
        recvdSize = 0;          # Reset received size
        droppedPackets = 0;     # Reset dropped packet counter
    }
}

END {
    # Final statistics calculation
    sum = 0;
    recvnum = 0;
    for (i in start_time) {
        start = start_time[i];
        end = end_time[i];
        packet_duration = end - start;
        if (packet_duration > 0) {
            sum += packet_duration;
            recvnum++;
        }
    }
    if (recvnum > 0) {
        delay = sum / recvnum;
    } else {
        delay = 0;
    }

    # Printing final statistics
    printf("Final stats - starttime: %.2f, stoptime: %.2f\n", starttime, stoptime);
    printf("Total Send Packets = %d\n", totalsend);
    printf("Total Received Packets = %d\n", totalrecv);
    printf("Average end to end delay(ms) = %.2f\n", delay * 1000);
    print("Total No. of dropped packets = ", totaldroppedPackets);
}
