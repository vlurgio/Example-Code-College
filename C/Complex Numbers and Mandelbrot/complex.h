
/*  User-defined complex number type */
typedef struct {
	double real, imag;
} complex_t;


/*
*  Operators to process complex numbers
*/
int scan_complex(complex_t *c);
void print_complex(complex_t c);
complex_t add_complex(complex_t c1, complex_t c2);
complex_t subtract_complex(complex_t c1, complex_t c2);
complex_t multiply_complex(complex_t c1, complex_t c2);
complex_t abs_complex(complex_t c);
