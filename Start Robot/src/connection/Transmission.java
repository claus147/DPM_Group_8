/**
* @author Sean Lawlor, Stepan Salenikovich
* @date November 3, 2011
* @class ECSE 211 - Design Principle and Methods
*/
package connection;

/**
 * Skeleton class to hold datatypes needed for final project
 * 
 * Simply all public variables so can be accessed with 
 * Transmission t = new Transmission();
 * int fx = t.fx;
 * 
 * and so on...
 * 
 * Also the role is an enum, converted from the char transmitted. (It should never be
 * Role.NULL)
 */

public class Transmission {
	/**
	 * The role, Defender or Attacker
	 */
	public PlayerRole role;
	/**
	 * Ball dispenser X tile position
	 */
<<<<<<< HEAD
=======
	
	public PlayerRole getPlayerRole(){
		return role;
	}
	
>>>>>>> update
	public int bx;
	/**
	 * Ball dispenser Y tile position
	 */
<<<<<<< HEAD
=======
	
	public int getBx(){
		return bx;
	}
	
>>>>>>> update
	public int by;
	/**
	 * Defender zone dimension 1
	 */
<<<<<<< HEAD
=======
	
	public int getBy(){
		return by;
	}
	
>>>>>>> update
	public int w1;
	/**
	 * Defender zone dimension 2
	 */
<<<<<<< HEAD
=======
	public int getW1(){
		return w1;
	}
	
>>>>>>> update
	public int w2;
	/**
	 * Forward line distance from goal
	 */
<<<<<<< HEAD
=======
	public int getW2(){
		return w2;
	}
	
>>>>>>> update
	public int d1;
	/**
	 * starting corner, 1 through 4
	 */
<<<<<<< HEAD
	public StartCorner startingCorner;
	
=======
	public int getD1(){
		return d1;
	}
	
	public StartCorner startingCorner;
	
	public StartCorner getStartCorner(){
		return startingCorner;
	}
>>>>>>> update
}
