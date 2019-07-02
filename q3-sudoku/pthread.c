#include <pthread.h>
#include <stdio.h>
#include <string.h>
#include <malloc.h>
#include <unistd.h>

/*shared data*/
int puzzle [9][9];

/*Each valid check variables*/
int rowValid = 0;
int colValid = 0;
int squareValid[9] = {0,0,0,0,0,0,0,0,0};
/*
 * workers' indexes
 * 1 2 3 
 * 4 5 6
 * 7 8 9
 */ 

void *rowrunner(void *param); /*the row thread*/
void *columnrunner(void *param); /*the column thread*/
void *squarerunner(void *param); /*the square thread*/

typedef struct{
	int row;
	int column;
	int number;
} parameters;


int main(int argc, char *argv[]){
	
	
		/*Reset global variables*/
		rowValid = 0;
		colValid = 0;
		int p;
		for(p=0;p<9;p++)
			squareValid[p] = 0;
			
		int validsquare[9][9] = {
			{6,2,4,5,3,9,1,8,7},
			{5,1,9,7,2,8,6,3,4},
			{8,3,7,6,1,4,2,9,5},
			{1,4,3,8,6,5,7,2,9},
			{9,5,8,2,4,7,3,6,1},
			{7,6,2,3,9,1,4,5,8},
			{3,7,1,9,5,6,8,4,2},
			{4,9,6,1,8,2,5,7,3},
			{2,8,5,4,7,3,9,1,6}
		};
		
		int invalidSquareA[9][9] = {
			{6,2,4,7,7,7,1,8,7},
			{5,1,9,7,7,7,6,3,4},
			{8,3,7,7,7,7,2,9,5},
			{1,4,3,8,6,5,7,2,9},
			{9,5,8,2,4,7,3,6,1},
			{7,6,2,3,9,1,4,5,8},
			{3,7,1,9,5,6,2,2,2},
			{4,9,6,1,8,2,2,2,2},
			{2,8,5,4,7,3,2,2,2}
		};
		
		int invalidSquareB[9][9] = {
			{1,2,3,1,2,3,1,2,3},
			{1,2,3,1,2,3,1,2,3},
			{1,2,3,1,2,3,1,2,3},
			{1,2,3,1,2,3,1,2,3},
			{1,2,3,1,2,3,1,2,3},
			{1,2,3,1,2,3,1,2,3},
			{1,2,3,1,2,3,1,2,3},
			{1,2,3,1,2,3,1,2,3},
			{1,2,3,1,2,3,1,2,3}
		};
		
		int invalidSquareC[9][9] = {
			{1,1,1,1,1,1,1,1,1},
			{2,2,2,2,2,2,2,2,2},
			{3,3,3,3,3,3,3,3,3},
			{4,4,4,4,4,4,4,4,4},
			{5,5,5,5,5,5,5,5,5},
			{6,6,6,6,6,6,6,6,6},
			{7,7,7,7,7,7,7,7,7},
			{8,8,8,8,8,8,8,8,8},
			{9,9,9,9,9,9,9,9,9}
		};
		
		printf("\nWelcome to 9x9 sudoku!!\n"
			"valid   sudoku - 1\n"
			"invalid sudoku - 2\n"
			"invalid sudoku - 3\n"
			"invalid sudoku - 4\n"
			"Exit - 0\n"
			"Enter a number: ");
			
		int choice;	
		scanf("%d", &choice);
		
		if(choice == 0){
			printf("Program terminated\n");
			return(0);
		}
		
		
		/*variables for use*/
		int i, j, k;
		int n = 9;

		if(choice == 2){
			for(i=0; i<n; i++){
				for(j=0; j<n; j++){
					puzzle[i][j] = invalidSquareA[i][j];
					}
				}
			}
		else if(choice == 3){
			for(i=0; i<n; i++){
				for(j=0; j<n; j++){
					puzzle[i][j] = invalidSquareB[i][j];
					}
				}
			}		
		else if(choice == 4){
			for(i=0; i<n; i++){
				for(j=0; j<n; j++){
					puzzle[i][j] = invalidSquareC[i][j];
					}
				}
			}	
		else{
			for(i=0; i<n; i++){
				for(j=0; j<n; j++){
					puzzle[i][j] = validsquare[i][j];
					}
				}
			}
					
				
		
		
		for(i=0; i<n; i++){
			if(i==0 || i==3|| i==6){
				for(k=0; k<n; k++)
					printf(" –");
				printf("\n");
			 }
			for(j=0;j<n;j++){
				if(j==0) printf("|");
				if(j%3!=2){
					printf("%d ", puzzle[i][j]);
					}
				else{
					printf("%d", puzzle[i][j]);
					printf("|");
					}
			}
			printf("\n");
		}
		for(k=0; k<n; k++)
		printf(" –");
		printf("\n");
		
		
		/*Threads start*/
		
		/*Thread for every row*/
		pthread_t tid; /* thread id */
		pthread_attr_t attr; /* thread attributes */
		pthread_attr_init(&attr); 
		pthread_create(&tid,&attr,rowrunner,(void*)1);
		
		/*Thread for every column*/
		pthread_t tid2; /* thread id */
		pthread_attr_t attr2; /* thread attributes */
		pthread_attr_init(&attr2); 
		pthread_create(&tid2,&attr2,columnrunner,(void*)1);
		
		/*Multiple threads for each square*/
		pthread_t tids [n];
		
		int num = 1;
		int r, c;
		int size = 9;
		for(r=0;r<size;r+=3){
			for(c=0;c<size;c+=3){
				//pthread_t tid3; /* thread id */
				pthread_attr_t attr3; /* thread attributes */
				pthread_attr_init(&attr3); 
				parameters *data = (parameters *) malloc (sizeof(parameters));
				data -> row = r;
				data -> column = c; 
				data -> number = num;
				pthread_create(&tids[num-1],&attr3,squarerunner,(void*)data);
				num++;
			}
		}
				
		
		/*Wait for every thread to finish*/
		pthread_join(tid,NULL);
		pthread_join(tid2,NULL);
		for(i=0;i<n;i++){
			pthread_join(tids[i],NULL);;
		}
		
		
		/*Check the result*/
		printf("row valid: %d\n", rowValid);
		printf("column valid: %d\n", colValid);
		printf("square valid:\n");
		for(i=0;i<n;i++){
			if(i%3==0)printf(" – – –\n");
			if(i%3==0)printf("|");
			printf("%d|",squareValid[i]);
			if(i%3 ==2) printf("\n");
		}
		printf(" – – –\n");
					
	
	/*Sudoku validity final check*/
	int pass = 1;
	if(!rowValid || !colValid) pass = 0;
	for(k=0;k<n;k++)
		if(!squareValid[k]) pass = 0;
	
	if(pass)
		printf("This puzzle is valid!!\n\n");
	else
		printf("This puzzle is invalid..\n\n");
	 
	char *done;
	printf("Enter any letter to terminate:");
	scanf("%s", done);
	 
    return 0;
}


/*Thread for each row*/
void *rowrunner(void*param)//row checker
{
	int n = 9;
	int rowcheck[n];
	int i;
	for(i=0;i<n;i++){
		rowcheck[i] = 0; //initialize by 0
	}

	int r, c;
	for(r=0;r<n;r++){
		for(c=0;c<n;c++){
			rowcheck[ puzzle[r][c] - 1] = 1;
		}
		for(c=0;c<n;c++){
			if(rowcheck[c] == 0) {
				rowValid = 0;
				pthread_exit(0);
			}
		}
		for(i=0;i<n;i++){
		rowcheck[i] = 0; //initialize by 0
		}
	}
	rowValid = 1;
	pthread_exit(0);

}

/*Thread for each column*/
void *columnrunner(void*param)
{

	int n = 9;
	int colcheck[n];
	int i;
	for(i=0;i<n;i++){
		colcheck[i] = 0; //initialize by 0
	}

	int r, c;
	for(c=0;c<n;c++){
		for(r=0;r<n;r++){
			colcheck[ puzzle[r][c] - 1] = 1;
		}
		for(r=0;r<n;r++){
			if(colcheck[r] == 0) {
				colValid = 0;
				pthread_exit(0);
			}
		}
		for(i=0;i<n;i++){
			colcheck[i] = 0; //initialize by 0
		}
	}
	colValid = 1;
	pthread_exit(0);

}

/*Thread for each square*/
void *squarerunner(void*param)
{
	parameters *dataptr = (parameters *) param;
	parameters data = *(dataptr);
	
	int n = 9;
	int squarechecker[n];
	int i;
	for(i=0;i<n;i++) squarechecker[n] = 0;
	
	int r, c;
	for(r = data.row; r < (data.row + 3); r++){
		for(c = data.column; c < (data.column + 3); c++){
			squarechecker[puzzle[r][c]-1] = 1;
		}
	}
	
	for(i=0;i<n;i++){
		if(squarechecker[i] == 0){
			pthread_exit(0);
		}
	}

	squareValid[data.number-1] = 1;
	pthread_exit(0);

}




