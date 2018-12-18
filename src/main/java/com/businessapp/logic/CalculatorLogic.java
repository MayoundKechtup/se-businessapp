package com.businessapp.logic;

import com.businessapp.Component;
import com.businessapp.ControllerIntf;
import com.businessapp.fxgui.CalculatorGUI_Intf;
import com.businessapp.fxgui.CalculatorGUI_Intf.Token;


/**
 * Implementation of CalculatorLogicIntf that only displays Tokens
 * received from the Calculator UI.
 *
 */
class CalculatorLogic implements CalculatorLogicIntf {
	private CalculatorGUI_Intf view;
	private StringBuffer dsb = new StringBuffer();
	private final double VAT_RATE = 19.0;
	private double ergebnis= 0;

	CalculatorLogic() {
	}

	@Override
	public void inject( ControllerIntf dep ) {
		this.view = (CalculatorGUI_Intf)dep;
	}

	@Override
	public void inject( Component parent ) {		
	}

	@Override
	public void start() {
		nextToken( Token.K_C );		// reset calculator
	}

	@Override
	public void stop() {
	}


	/**
     * Process next token received from UI controller.
     * <p>
     * Tokens are transformed into output into UI properties:
     * 	- CalculatorIntf.DISPLAY for numbers and
     * 	- CalculatorIntf.SIDEAREA for VAT calculations.
     * <p>
     * @param tok the next Token passed from the UI, CalculatorViewController.
     */
	public void nextToken( Token tok ) {
		try {
			switch( tok ) {
			case K_0:	appendBuffer( "0" ); break;
			case K_1:	appendBuffer( "1" ); break;
			case K_2:	appendBuffer( "2" ); break;
			case K_3:	appendBuffer( "3" ); break;
			case K_4:	appendBuffer( "4" ); break;
			case K_5:	appendBuffer( "5" ); break;
			case K_6:	appendBuffer( "6" ); break;
			case K_7:	appendBuffer( "7" ); break;
			case K_8:	appendBuffer( "8" ); break;
			case K_9:	appendBuffer( "9" );
				break;

			case K_1000:appendBuffer( "000" );
				break;

			case K_DIV:
				throw new ArithmeticException( "ERR: div by zero" );
			case K_MUL:	appendBuffer( "*" );
			; break;
			
			case K_PLUS: appendBuffer( "+" );
			/*
			String zeile = dsb.toString(); 
			while(zeile.endsWith("++")) {
				zeile.replace("+", "");
			}
			*/
			break;
			
			case K_MIN:	appendBuffer( "-" ); break;
			
			case K_EQ:	appendBuffer( "=" );
			addieren();
			subtrahieren();
			multiplizieren();
			;
			 
			
			
			break;

			case K_VAT:
				double mwst = (double) (Double.parseDouble(dsb.toString())*0.19);
				double brutto = Double.parseDouble(dsb.toString());
				double netto= brutto - mwst;  
				view.writeSideArea(
					"Brutto:"  + brutto +"€" + "\n" +
					VAT_RATE + "% MwSt:" + mwst +"€"  + "\n" +
					"Netto:" + netto +"€"
				);
				break;

			case K_DOT:	appendBuffer( "." );
				break;

			case K_BACK:
				dsb.setLength( Math.max( 0, dsb.length() - 1 ) );
				break;

			case K_C:
				view.writeSideArea( "" );
			case K_CE:
				dsb.delete( 0,  dsb.length() );
				break;

			default:
			}
			String display = dsb.length()==0? "0" : dsb.toString();
			view.writeTextArea( display );

		} catch( ArithmeticException e ) {
			view.writeTextArea( e.getMessage() );
		}
	}

	/*
	 * Private method(s).
	 */
	private void appendBuffer( String d ) {
		if( dsb.length() <= CalculatorGUI_Intf.DISPLAY_MAXDIGITS ) {
			
			if(d =="+" || d =="-" || d== "*" || d== "/" || d=="=" ) {
				String opperator = dsb.toString();
				
				if(!(opperator.endsWith("+")||opperator.endsWith("-") ||opperator.endsWith("*")||opperator.endsWith("/"))) {
					dsb.append(d);
				}
				
			}else {
				dsb.append(d);
			}
			
		}
	}
	private void subtrahieren() {
		String zeile1=dsb.toString();
		String zeile;
		String teil1;
		String teil2;
		double part1;
		double part2;
		if(zeile1.contains("-")) 
		{
			//teile
			zeile=zeile1.replaceAll("=", "");
			teil1=zeile.substring(0, zeile.indexOf("-"));
			zeile.replace("=", "");
			teil2=zeile.substring(zeile.indexOf("-")+1,zeile.length());
			
			//addiere
			part1=Double.parseDouble(teil1);
					part2=Double.parseDouble(teil2);
					
					ergebnis=part1-part2;
					view.writeTextArea(dsb.append(ergebnis).toString());
		}
		else if(zeile1.contains("*")||zeile1.contains("+"))
		{
		//view.writeSideArea("keine Subtraktion");
		}
			else{
			view.writeSideArea("Syntax Error");
		}	
	}
	private void addieren() {
		String zeile1=dsb.toString();
		String zeile;
		String teil1;
		String teil2;
		double summand;
		double summand2;
		if(zeile1.contains("+")) 
		{
			//teile
			zeile=zeile1.replaceAll("=", "");
			teil1=zeile.substring(0, zeile.indexOf("+"));
			zeile.replace("=", "");
			teil2=zeile.substring(zeile.indexOf("+")+1,zeile.length());
			
			//addiere
			summand=Double.parseDouble(teil1);
					summand2=Double.parseDouble(teil2);
					
					ergebnis=summand+summand2;
					view.writeTextArea(dsb.append(ergebnis).toString());
		}
		else if(zeile1.contains("*")||zeile1.contains("-"))
		{
		//view.writeSideArea("keine Addition");
		}
		else 
		{
			view.writeSideArea("Syntax Error");
		}
		
	}

	private void multiplizieren() {
		String zeile1=dsb.toString();
		String zeile;
		String teil1;
		String teil2;
		double part1;
		double part2;
		if(zeile1.contains("*")) 
		{
			//teile
			zeile=zeile1.replaceAll("=", "");
			teil1=zeile.substring(0, zeile.indexOf("*"));
			zeile.replace("=", "");
			teil2=zeile.substring(zeile.indexOf("*")+1,zeile.length());
			
			//addiere
			part1=Double.parseDouble(teil1);
					part2=Double.parseDouble(teil2);
					
					ergebnis=part1*part2;
					view.writeTextArea(dsb.append(ergebnis).toString());
		}
		else if(zeile1.contains("-")||zeile1.contains("+"))
		{
		//view.writeSideArea("keine Multiplikation");
		}
		else 
		{
			view.writeSideArea("Syntax Error");
		}
	}
}
