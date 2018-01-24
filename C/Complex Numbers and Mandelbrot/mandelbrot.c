#include "mandelbrot.h"

// Recursive Function demonstrating the mandelbrot set
complex_t mandelbrot(int n, complex_t c)
{
	if (n == 0)
	{
		return c;
	}
	else
	{
		complex_t a = mandelbrot((n - 1), c);
		return add_complex(multiply_complex(a, a), c);
	}
}

