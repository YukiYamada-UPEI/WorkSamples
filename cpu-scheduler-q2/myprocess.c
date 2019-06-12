#include "myprocess.h"
#include "data.h"


my_process* create_process(char *line, my_process *p){
	int p_data[100];
	
	char data[250];
	strcpy(data, line);
	int i=0;
	char *word;
	word = strtok(data, " ");
	while(word != NULL){
		int pid = strtol(word, NULL, 10);
		p_data[i] = pid; //pid is stored
		i++;
		word = strtok(NULL, " ");
	}
	int n = i;
		
	//assign the data to a process
	p->pid = p_data[0];
	p->arrival = p_data[1];
	p->n = 0;
	p->i = 0;
	p->t = 0;
	i=2;
	while(i<n){
		p->burst[i-2] = p_data[i];
		p->n++;
		i++;
	}
	return p;
}


void process_removed(my_process *p){
	p->i++;
	p->t = (p->t + 1) %2;
}

int isdone(my_process* p){
	return (p->burst[p->i] == -99);
}

void calctotalburst(my_process* p){
	int total=0;
	int i = 0;
	while(p->burst[i] != -99){
		total += p->burst[i];
		i++;
	}
	p->totalburst = total;
}

void finish(my_process*p, int totaltime){
	p->finish = totaltime;
	p->turnaround = totaltime - p->arrival;
	calctotalburst(p);
	p->waiting = p->turnaround - p->totalburst;
	
}

void print_presult(my_process* p){
	printf("P%-2d Turnaround=%-5dWaiting=%-5d\n" ,p->pid, p->turnaround, p->waiting);
}

void print_pinfo(my_process* p){
	printf("   P%-2d arrived=%-5dfinished=%-5d" ,p->pid,p->arrival, p->finish);
	printf("totalburst(cpu+io)=%d\n", p->totalburst);
}

void finish_msg(my_process* p){
	printf("   pid %d finished\n", p->pid);
}



void printp(my_process *P1){
	printf("   pid%d %4d sec ",P1->pid, P1->arrival);
	int i=0;
	for(i=0; i<P1->n; i++){
		printf("(%d)",P1->burst[i]);
	}
	printf(" n=%d, i=%d, t=%d\n", P1->n, P1->i, P1->t);

}



