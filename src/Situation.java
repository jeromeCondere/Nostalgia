/*
An agent has a perception fonction
*/

public class Situation 
{
 public static final int CRITICAL=20;
 public static final int GOOD=21;
 public static final int BAD=22;
 public static final int NONE=23;
 public static final int  NEGOCIATION=24;
 public static final int AGREEMENT=25;
 public static final int APOLOGIZE=26;
 public static final int VIOLENT=27;
 public static final int PEACEFUL=28;
 public static final int NON_EXPLAINABLE=29;
 public static final int SAD=30;
 public static final int TENSE=31;
 public static final int  GHOST=32;
 public static final int  ECHEC=32;
 public static final int  NULL=-1;
 protected int state;
 public Situation(int state)
 {
	 this.state=state;
 }
 
 public int getState()
 {
	 return this.state;
 }
 /*
  * there is not setter because a sitation is considered
  * as a picture of an environnement at an instant t0
  * thus once a situation is set we can't change it
  */
}

