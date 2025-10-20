/* demo.c */

#include "lp_lib.h"
int demo()
{
  lprec *lp;
  int Ncol, *colno = NULL, j, ret = 0;
  REAL *row = NULL;

  /* We will build the model row by row
     So we start with creating a model with 0 rows and 2 columns */
  Ncol = 3; /* there are three variables in the model */
  lp = make_lp(0, Ncol);
  if(lp == NULL)
    ret = 1; /* couldn't construct a new model... */

  if(ret == 0) {
    /* let us name our variables. Not required, but can be useful for debugging */
    set_col_name(lp, 1, "x");
    set_col_name(lp, 2, "y");
    set_col_name(lp, 3, "z");

    /* create space large enough for one row */
    colno = (int *) malloc(Ncol * sizeof(*colno));
    row = (REAL *) malloc(Ncol * sizeof(*row));
    if((colno == NULL) || (row == NULL))
      ret = 2;
  }

  if(ret == 0) {
    set_add_rowmode(lp, TRUE);  /* makes building the model faster if it is done rows by row */

    /* construct first row (2x + 3y + z <= 100) */
    j = 0;

    colno[j] = 1; row[j++] = 2; /* Product A*/
    colno[j] = 2; row[j++] = 3; /* Product B*/
    colno[j] = 3; row[j++] = 1; /* Product C*/
    if (!add_constraintex(lp, j, row, colno, LE, 100)) {
      ret = 3;
    }

    /* construct second row (3x + 2y + 2z <= 150) */
    j = 0;

    colno[j] = 1; row[j++] = 3; /* Product A*/
    colno[j] = 2; row[j++] = 2; /* Product B*/
    colno[j] = 3; row[j++] = 2; /* Product C*/
    if (!add_constraintex(lp, j, row, colno, LE, 150)) {
      ret = 3;
    }

    /* construct third row (1x + 2y + 3z <= 120) */
    j = 0;

    colno[j] = 1; row[j++] = 1; /* Product A*/
    colno[j] = 2; row[j++] = 2; /* Product B*/
    colno[j] = 3; row[j++] = 3; /* Product C*/
    if (!add_constraintex(lp, j, row, colno, LE, 120)) {
      ret = 3;
    }
  }

  if(ret == 0) {
    set_add_rowmode(lp, FALSE);  /* Turn off rowmode after building the model */
    /* construct second row (10x + 12y + 8z) */
    j = 0;

    colno[j] = 1; row[j++]= 10; /* Product A */
    colno[j] = 2; row[j++]= 12; /* Product B */
    colno[j] = 3; row[j++]= 8; /* Product C */

    if (!set_obj_fnex(lp, j, row, colno)) {
      ret = 4;
    }
  }

  if (ret == 0) {
    /* Set the object direction to maximize */
    set_maxim(lp);

    /* Set variables as integers */
    set_int(lp, 1, TRUE); /* Product A (x) as an integer */
    set_int(lp, 2, TRUE); /* Product B (y) as an integer */
    set_int(lp, 3, TRUE); /* Product C (z) as an integer */

    /* Solve the problem */
    ret = solve(lp);
    if (ret == OPTIMAL) {
        ret = 0;
    } else {
        ret = 5;
    }
}

  if(ret == 0) {
    /* a solution is calculated, now lets get some results */

    /* objective value */
    printf("Objective value: %f\n", get_objective(lp));

    /* variable values */
    get_variables(lp, row);
    for(j = 0; j < Ncol; j++)
      printf("%s: %f\n", get_col_name(lp, j + 1), row[j]);

    /* we are done now */
  }

  /* free allocated memory */
  if(row != NULL)
    free(row);
  if(colno != NULL)
    free(colno);

  if(lp != NULL) {
    /* clean up such that all used memory by lpsolve is freed */
    delete_lp(lp);
  }

  return(ret);
}

int main()
{
  demo();
}
