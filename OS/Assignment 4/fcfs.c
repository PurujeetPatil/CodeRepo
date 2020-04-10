#include<stdio.h>

struct process{
	int at, bt, ct, tat, wt;
};

void sort(struct process*, int);
void display(struct process*, int);
void calculate(struct process*, int);
void input(struct process*, int);

void main(){
	int num;
	printf("Enter number of process : ");
	scanf("%d", &num);
	struct process p[num];
	input(p, num);
	sort(p, num);
	calculate(p, num);
	display(p, num);
	printf("\n");
}

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

void input(struct process *p, int num) {
	for(int i=0; i<num; i++){
		printf("Process #%d\n", i+1);
		printf("Arrival Time : ");
		scanf("%d", &p[i].at);
		printf("Burst Time : ");
		scanf("%d", &p[i].bt);
	}
}

void calculate(struct process* p, int num) {
	for(int i=0; i<num; i++) {
		if(i==0) {
			p[i].ct = p[i].at + p[i].bt;
			p[i].wt = 0;
		}
		else {
			if(p[i-1].ct >= p[i].at) {
				p[i].ct = p[i].bt + p[i-1].ct;
				p[i].wt = p[i-1].ct - p[i].at;
			}
			else {
				p[i].ct = p[i].at + p[i].bt;
				p[i].wt = 0;
			}
		}
		p[i].tat = p[i].ct - p[i].at;
	}
}

void display(struct process *p, int num) {
	printf("GANTT\nAT\tBT\tCT\tTAT\tWT\n");
	for(int i=0; i<num; i++){
		printf("%d\t", p[i].at);
		printf("%d\t", p[i].bt);
		printf("%d\t", p[i].ct);
		printf("%d\t", p[i].tat);
		printf("%d\t", p[i].wt);
		printf("\n");
	}
}

void sort(struct process *arr, int num){
	struct process temp;
	for(int i=0; i<num; i++){
		for(int j=1; j<num; j++){
			if(arr[j].at<arr[j-1].at){
				temp = arr[j];
				arr[j] = arr[j-1];
				arr[j-1] = temp;
			}
		}
	}
}