#include <stdio.h>
#include <stdlib.h>
#include <stdbool.h>
#include <windows.h>

#define CRT_SECURE_NO_WARNINGS
#define COUNT 10

struct nodeStruct
{
	int data;
	struct nodeStruct* rightChild;
	struct nodeStruct* leftChild;
};
typedef struct nodeStruct node;

node* insert(int key, node* root);
int _print_t(node* root, int is_left, int offset, int depth, char s[20][255]);
void print_t(node* root);
int maxDepth(node* root);
void treeprint(node* p, int level);


void main() {
	SetConsoleCP(1251);
	SetConsoleOutputCP(1251);
	while (true)
	{
		node* root = NULL;
		int amount = 0;
		printf("Колличество элементов в дереве (больше 16 не недо))\n");
		printf("(сами элементы создаются случайно в диапазоне [0,99]):\n");
		scanf_s("%d", &amount);

		srand(time(NULL));
		int key;
		for (int i = 0; i < amount; i++)
		{
			key = rand() % 101 - 50;
			root = insert(key, root);
		}

		int l = 3;
		print_t(root);
		//treeprint(root, 1);

		printf("\nМаксимальная глубина: %d\n", maxDepth(root));
	}
}

node* insert(int key, node* root) {
	if (root == NULL)
	{
		root = (node*)malloc(sizeof(node));
		root->data = key;
		root->leftChild = NULL;
		root->rightChild = NULL;
	}
	else if (key < root->data)
	{
		root->leftChild = insert(key, root->leftChild);
	}
	else
	{
		root->rightChild = insert(key, root->rightChild);
	}
	return root;
}

int _print_t(node* root, int is_left, int offset, int depth, char s[20][255]) {
	char b[20];
	int width = 5;

	if (!root) return 0;

	sprintf_s(b, "(%03d)", root->data);

	int left = _print_t(root->leftChild, 1, offset, depth + 1, s);
	int right = _print_t(root->rightChild, 0, offset + left + width, depth + 1, s);

#ifdef COMPACT
	for (int i = 0; i < width; i++)
		s[depth][offset + left + i] = b[i];

	if (depth && is_left) {

		for (int i = 0; i < width + right; i++)
			s[depth - 1][offset + left + width / 2 + i] = '-';

		s[depth - 1][offset + left + width / 2] = '.';

	}
	else if (depth && !is_left) {

		for (int i = 0; i < left + width; i++)
			s[depth - 1][offset - width / 2 + i] = '-';

		s[depth - 1][offset + left + width / 2] = '.';
	}
#else 
	for (int i = 0; i < width; i++)
		s[2 * depth][offset + left + i] = b[i];

	if (depth && is_left) {

		for (int i = 0; i < width + right; i++)
			s[2 * depth - 1][offset + left + width / 2 + i] = '-';

		s[2 * depth - 1][offset + left + width / 2] = '+';
		s[2 * depth - 1][offset + left + width + right + width / 2] = '+';

	}
	else if (depth && !is_left) {

		for (int i = 0; i < left + width; i++)
			s[2 * depth - 1][offset - width / 2 + i] = '-';

		s[2 * depth - 1][offset + left + width / 2] = '+';
		s[2 * depth - 1][offset - width / 2 - 1] = '+';
	}
#endif
	return left + width + right;
}

void print_t(node* root) {
	//char s[20][255];
	/*char s = (char)malloc(sizeof(char), 20);
	for (int i = 0; i < 20; i++)
	{
		s[i] = (char)malloc(sizeof(char), 255);
	}*/
	for (int i = 0; i < 20; i++)
	{
		sprintf_s(s[i], "%80s", " ");
	}
	_print_t(root, 0, 0, 0, s);
	for (int i = 0; i < 20; i++)
	{
		printf("%s\n", s[i]);
	}
}

void treeprint(node* p, int level) {
	if (p != NULL) {

		treeprint(p->rightChild, level + 1);
		for (int i = 0; i < level; i++) printf(" ");
		printf("%d\n", p->data);
		treeprint(p->leftChild, level + 1);
	}
}

int maxDepth(node* root) {
	if (root == NULL) {                      //сделать вывод максимального пути
		return 0;
	}

	int leftDepth = maxDepth(root->leftChild);
	int rightDepth = maxDepth(root->rightChild);

	return max(leftDepth, rightDepth) + 1;
}