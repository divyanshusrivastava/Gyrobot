#include<avr/io.h>
#include<util/delay.h>


void forward()	// verified
{
	PORTB = 0b00001000;
	PORTD = 0b00010000;

}

void backward()		// verified
{
	PORTB = 0b00000000;
	PORTD = 0b10100000;
}

void stop()		// hard stop
{
	PORTB = 0b00001000;
	PORTD = 0b10110000;

}

void left()		// verified
{
	PORTB = 0b00000000;
	PORTD = 0b00010000;

}

void right()	// verified
{
	PORTB = 0b00001000;
	PORTD = 0b00000000;

}

void port_init()
{
	DDRB |= 0b00001000;
	DDRD |= 0b10110000;
}
	



void main ()
{

	port_init();
	
	while(1)
	{
	
		forward();

		_delay_ms(2000);

		stop();

		_delay_ms(500);
	
		backward();

		_delay_ms(2000);

		stop();

		_delay_ms(500);

		left();

		_delay_ms(2000);

		stop();

		_delay_ms(500);

		right();

		_delay_ms(2000);

		stop();

		_delay_ms(500);
	}
}
