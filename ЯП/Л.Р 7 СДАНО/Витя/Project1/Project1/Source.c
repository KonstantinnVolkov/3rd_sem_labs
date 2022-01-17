#define CRT_SECURE_NO_WARNINGS

#include <stdio.h>
#include <stdlib.h>
#include <stdbool.h>
#include <windows.h>

struct nodeStruct
{
	int data;
	int min;
	struct nodeStruct* next;
};
typedef struct nodeStruct node;

struct queueStruct
{
	node* front;
	node* rear;
};
typedef struct queueStruct queue;

bool isEmpty(queue* q);
void enqueue(queue* q, int data);  //add
void dequeue(queue* q);		//delete
void showQueue(queue* q);  
int showMenu();
queue* task(queue* q);

void main() {
	SetConsoleCP(1251);
	SetConsoleOutputCP(1251);

	queue* q;
	q = malloc(sizeof(queue));
	q->front = NULL;
	q->rear = NULL;
	node* head = NULL;
	node* tail = NULL;
	int value, action;

	while (true)
	{
		action = showMenu();

		switch (action)
		{
		case 1:
			printf("Enter number: ");
			scanf("%d", &value);
			printf("\n");
			enqueue(q, value);
			break;
		case 2:
			dequeue(q);
			break;
		case 3:
			printf("Queue content: ");
			showQueue(q);
			break;
		case 4:
			q = task(q);  
			break;
		case 5:
			system("cls");
			break;
		case 6:
			exit(0);
		}
	}
}

int showMenu() {
	printf("Menu:\n");
	printf("  1: Add\n");
	printf("  2: Delete\n");
	printf("  3: Print\n");
	printf("  4: Task\n");
	printf("  5: Clear console\n");
	printf("  6: Exit\n");
	printf("Choose option: ");
	int action;
	scanf("%d", &action);
	printf("\n");

	return action;
}

bool isEmpty(queue* q)
{
	return (q->rear == NULL);
}

void enqueue(queue* q, int value) {
	node* tmp;
	tmp = (node*)malloc(sizeof(node));
	tmp->data = value;
	tmp->next = NULL;
	if (isEmpty(q))
	{
		q->front = q->rear = tmp;
	}
	else {
		q->rear->next = tmp;
		q->rear = tmp;
	}
}

void dequeue(queue* q) {
	node* tmp;
	tmp = q->front;
	if (q->front != NULL)
	{
		q->front = q->front->next;
		free(tmp);
	}
	else
	{
		printf("Queue is empty. Nothing to delete!\n\n");
	}
}

void showQueue(queue* q) {
	node* tmp;
	tmp = q->front;
	if (q->front != NULL)
	{
		int queueLength = 0;
		while (tmp != NULL)
		{
			printf("%d ", tmp->data);
			tmp = tmp->next;
			queueLength++;
		}
		printf("\nQueue length: %d\n\n", queueLength);
	}
	else
	{
		printf("Queue is empty. Nothing to display!\n\n");
	}
}

queue* task(queue* q) {

	queue* bufQueue;							//	Создаем			
	bufQueue = malloc(sizeof(queue));			//	новую
	bufQueue->front = NULL;						//	очередь
	bufQueue->rear = NULL;						//
	node* bufHead = NULL;						//	все ещё
	node* bufTail = NULL;						//	создаем....

	node* tmp;				//переменная для прохода по главной очереди
	tmp = q->front;

	if (q->front != NULL)
	{
		while (tmp != NULL)  //цикл по главной очереди
		{
			if (tmp->data >= 0)
			{
				enqueue(bufQueue, tmp->data);  //пихаем нащ неотрицательный элемент из главной очереди в новую буферную
			}
			tmp = tmp->next;
		}
		return bufQueue;	//возвращаем буферную очередь, которая переприсваивается при вызове функции
	}
	else
	{
		printf("Main queue is empty. Nothing to do!\n\n");
	}
}


