#include<stdio.h>
#include<stdlib.h>
#include<unistd.h>

void desc_sort();

void main(){
	int num, *arr, ch_PID;
	printf("Enter size of array : ");
	scanf("%d", &num);
	arr = malloc (num * sizeof(int));
	for(int i=0; i<num; i++)
		scanf("%d", &arr[i]);
	printf("Array : \n");
	for(int i=0; i<num; i++)
		printf("%d", arr[i]);
	
	char *args[num+2];
	
	char x[2];
	sprintf(x, "%d", num);
	args[0] = x;
	
	char y[num][sizeof(int)];
	for(int i=0; i<num; i++){
		sprintf(y[i], "%d", arr[i]);
		args[i+1] = y[i];
	}
	args[num+2] = NULL;
	printf("\nArgs : \n");
	for(int i=0; i<num+2; i++){
		printf("%s", args[i]);
	}
	
	ch_PID = fork();
	if(ch_PID >= 0){
		if(ch_PID == 0)
			execv("asc_sort", args);
		else{
			printf("\nSleeping...\n");
			sleep(5);
			desc_sort(num, arr);
		}
	}
	else
		printf("Forking failed\n");
}

void desc_sort(int num, int* arr){
	int temp;
	for(int i=0; i<num; i++){
		for(int j=1; j<num; j++){
			if(arr[j]>arr[j-1]){
				temp = arr[j];
				arr[j] = arr[j-1];
				arr[j-1] = temp;
			}
		}
	}
	
	printf("\nDescending order : \n");
	for(int i=0; i<num; i++)
		printf("%d ", arr[i]);
	printf("\n");
}