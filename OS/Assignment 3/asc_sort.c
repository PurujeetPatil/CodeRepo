#include<stdio.h>
#include<stdlib.h>
#include<string.h>

void main(int argc, char* argv[]){
	int num = atoi(argv[0]);
	int arr[num], temp;
	for(int i=0; i<num; i++){
		arr[i] = atoi(argv[i+1]);
	}
	
	for(int i=0; i<num; i++){
		for(int j=1; j<num; j++){
			if(arr[j]<arr[j-1]){
				temp = arr[j];
				arr[j] = arr[j-1];
				arr[j-1] = temp;
			}
		}
	}
	
	printf("Ascending Order : \n");
	for(int i=0; i<num; i++)
		printf("%d ", arr[i]);
}