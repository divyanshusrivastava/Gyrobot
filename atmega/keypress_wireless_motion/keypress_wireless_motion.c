#include<avr/io.h>
#define F_CPU 16000000
#include<util/delay.h>

void USARTInit () {
   UBRRL = 51;
   UBRRH = 0;
   UCSRC=(1<<URSEL)|(3<<UCSZ0);
   UCSRB=(1<<TXCIE)|(1<<TXEN)|(1<<RXEN)|(1<<RXCIE);
}

char USARTReadChar()
{
   while(!(UCSRA & (1<<RXC)))
   {
   }
   return UDR;
}

void USARTWriteChar(char data)
{
	while(!(UCSRA & (1<<UDRE)))
   {
   }
	UDR=data;
}


void motion_pin_config()
{
	DDRB |= 0b00001000;
	DDRD |= 0b10110000;
}

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


void main () {
	USARTInit();
	motion_pin_config();

	char data;
	
	while(1) {
		data = USARTReadChar();
		_delay_ms(500);

				

		USARTWriteChar(data);
		

		if (data == 'F')	{
			stop();
			_delay_ms(500);
			forward();
			_delay_ms(2000);
			stop();
			_delay_ms(500);
			
		}

		if (data == 'B') {
			stop();
			_delay_ms(500);
			backward();
			_delay_ms(2000);
			stop();
			_delay_ms(500);
		}

		if (data == 'L') {
			stop();
			_delay_ms(500);
			left();
			_delay_ms(2000);
			stop();
			_delay_ms(500);
		}

		if (data == 'R') {
			stop();
			_delay_ms(500);
			right();
			_delay_ms(2000);
			stop();
			_delay_ms(500);
		}
		
	}
}
