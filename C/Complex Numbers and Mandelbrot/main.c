/*
* Vinny Lurgio
*
* Draws the mandelbrot set to the screen
*
*/
#include <stdio.h>
#include "mandelbrot.h"
//draws the mandelbrot set to the screen 
int main(void)
{
	int numStepsY = 100;
	int numStepsX = 100;
	double stepSizeX = (0.47 + 2) / numStepsX;
	double stepSizeY = (1.12 + 1.12) / numStepsY;
	double i = -2;
	
	double j = -1.27;

	for (i = -2; i <= 0.47; i += stepSizeX)
	{
		for (j = -1.27; j <= 2; j += stepSizeY)
		{
			complex_t c;
			
			c.real = i;
			c.imag = j;
			if (abs_complex(mandelbrot(15, c)).real
 < 10000)
			{
				printf("#");
			}
			else
			{
				printf(" ");
			}
		}
		printf("\n");
	}


}
