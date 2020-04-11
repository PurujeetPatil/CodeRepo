#include<stdio.h>
#include<stdlib.h>
#include<pthread.h>

//Function declaration
void accept(int**, int, int);
void display(int**, int, int);
int** initMat(int**, int, int);
void* run(void* param);

struct info {
	int row, col;
};

struct info *tempMat, *calMat;

int row, col, col2, k=0, sum1=0, sum2=0;
int **mat1, **mat2, **result;

int main(int argc, char *argv[]) {
	pthread_t th_id;
	void* arg;
	
	//Accepting no. of rows and cols and initializing matrices
	printf("Enter number of rows and columns : ");
	scanf("%d%d", &row, &col);
	mat1 = initMat(mat1, row, col);
	mat2 = initMat(mat2, row, col);
	result = initMat(result, row, col);
	
	//Accepting elements for matrices
	printf("Enter elements for first matrix : \n");
	accept(mat1, row, col);
	printf("Enter elements for second matrix : \n");
	accept(mat2, row, col);
	
	//Creating thread for each operation for multiplication
	for(int i = 0; i < row; i++) {
      	for(int j = 0; j < col; j++) {
			tempMat = (struct info *) malloc(sizeof(struct info)); 
         	tempMat->row = i;  
         	tempMat->col = j;  
         
         	pthread_create(&th_id, NULL, run, tempMat);
			pthread_join(th_id,&arg); 
			k = *((int *)arg); 
			sum1 = sum1 + k;  
			result[tempMat->row][tempMat->col] = k; 
			printf("k is %d\n ",k);  
      	}
	}
	
	printf("\n\nResult\n");
	display(result, row, col);
	return 0;

}


void accept(int** mat, int row, int col) {
	for(int i=0; i<row; i++)
		for(int j=0; j<col; j++)
			scanf("%d", &mat[i][j]);
}

void display(int** mat, int row, int col) {
	for(int i=0; i<row; i++) {
		for(int j=0; j<col; j++)
			printf("%d ", mat[i][j]);
		printf("\n");
	}
}

int** initMat(int** x, int rows, int cols) {
	x = malloc(rows * sizeof(int));
	for (int i=0; i<rows; i++) {
		x[i] = malloc(cols * sizeof *x[i]);
	}
	return x;
}

void* run(void* param) {
	calMat = param; 
  	sum2=0;
   	
   	for(int n = 0; n< row; n++){
      		sum2 += mat1[calMat->row][n] * mat2[n][calMat->col];
   	}
	printf("sum is %d\n ", sum2);   
	pthread_exit(&sum2); 

}