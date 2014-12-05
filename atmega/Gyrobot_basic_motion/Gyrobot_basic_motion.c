#include<avr/io.h>
#include<util/delay.h>

void main ()
{
	DDRB = 0b00010000;
	DDRD = 0b10110000;
	
	while (1) 
	{
		PORTB = 0b00010000;
		PORTD = 0b00010000;

		_delay_ms(1000);

		PORTB = 0b00000000;
		PORTD = 0b10100000;

		_delay_ms(1000);

	}

}
