#include "result.h"

void reset_result(RESULT *result){
	result -> totaltime = 0;
	result -> n = 0;
	result -> ave_turnaround = 0;
	result -> ave_wait = 0;      
	result -> throughput = 0;	    

}

void put_finished(RESULT *result, my_process *p){
	result->array[p->pid-1] = p;
	result->n++;
}

void calc_result(RESULT *result){
	int totaltime = result ->totaltime;
	int n = result->n; //number of processes

	int total_turnaround = 0;
	int total_wait =0;
	int i = 0;
	while(i < n){
		total_turnaround += result->array[i]->turnaround;
		total_wait += result->array[i]->waiting;
		i++;
	}
	result -> ave_turnaround = (float)total_turnaround / (float)n;
	result -> ave_wait = (float)total_wait / (float)n;
	result -> throughput = (float)n / (float)totaltime;
}

void print_pinfos(RESULT *result){
	int i = 0;
	while(i < result->n){
		print_pinfo(result->array[i]);
		i++;
	}
	printf("\n");
}

void print_result(RESULT *result){
	calc_result(result);
	printf("RESULT: \n");
	printf("TOTAL TIME: %d sec\n", result->totaltime);
	printf("=============================================\n");
	int i=0;
	while(i < result->n){
		print_presult(result->array[i]);
		i++;
	}
	printf("Ave. Turnaround = %5.2f\n", result->ave_turnaround);
	printf("Ave. Waiting    = %5.2f\n", result->ave_wait);
	printf("Throughput      = %d/%d\n", result->n, result->totaltime);
	printf("=============================================\n");

}




