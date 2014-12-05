#include<avr/io.h>
#include<util/delay.h>

void USARTInit () {
   UBRRL = 103;
   UBRRH = 0;


   UCSRC=(1<<URSEL)|(3<<UCSZ0);
   UCSRB=(1<<TXCIE)|(1<<TXEN)|(1<<RXEN)|(1<<RXCIE);
}

char USARTReadChar()
{
   while(!(UCSRA & (1<<RXC)))
   {
      //Do nothing
   }

   //Now USART has got data from host
   //and is available is buffer

   return UDR;
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

	DDRA = 0x03;
	char data;
	
	while(1) {
		data = USARTReadChar();
		_delay_ms(500);
		
		if(data)
		{
			forward();
			_delay_ms(2000);
		}


		if (data == '1')	{
			forward();
			_delay_ms(2000);
			
		}

		if (data == '2') {
			backward();
			_delay_ms(2000);
		}

		if (data == '3') {
			left();
			_delay_ms(2000);
		}

		if (data == '4') {
			right();
			_delay_ms(2000);
		}
		
	}
}
