// (C) 1998-2014 Information Desire Software GmbH
// www.infodesire.com

package com.infodesire.tablestream;

import com.infodesire.commons.JAVA;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;


/**
 * Row of data
 *
 */
@XStreamAlias( "r" )
public class Row implements Serializable, Iterable<Cell> {

  
  private static final long serialVersionUID = 1L;
  
  
  @XStreamImplicit
  private List<Cell> cells;

  
  @XStreamImplicit
  private Set<Property> properties;


  /**
   * Index of this row in the original order. This optional number will
   * be used when rows have equal values (equality) or equal sort values (comparable).
   * 
   */
  @XStreamAlias( "i" )
  @XStreamAsAttribute
  private Integer originalIndex;
  
  
  public Row() {}
  
  
  public Row( Cell... cells ) {
    for( Cell cell: cells ) {
      add( cell );
    }
  }
  
  
  private List<Cell> getCells() {
    if( cells == null ) {
      cells = new ArrayList<Cell>();
    }
    return cells;
  }
  
  
  public void add( Cell cell ) {
    getCells().add( cell );
  }
  

  public Cell getCell( int index ) {
    return getCells().get( index );
  }


  public boolean hasCell( int index ) {
    return index < getCells().size() && index >= 0;
  }


  public int size() {
    return getCells().size();
  }
  
  
  public int hashCode() {
    return getCells().hashCode();
  }
  
  /**
   * @param row Other row
   * @return Two rows are equal if all their cells are equal and the number of their cells is equal. 
   * 
   */
  public boolean equals( Object o ) {
    if( o instanceof Row ) {
      Row row = (Row) o;
      if( size() == row.size() ) {
        for( int i = 0; i < size(); i++ ) {
          if( !getCell( i ).equals( row.getCell( i ) ) ) {
            return false;
          }
        }
        return JAVA.equal( originalIndex, row.originalIndex );
      }
    }
    return false;
  }
  
  
  public String toString() {
    return "" + getCells();
  }
  

  /**
   * Set property
   * 
   * @param key Key 
   * @param value Value
   * 
   */
  public void setProperty( String key, String value ) {
    if( properties == null ) {
      properties = new HashSet<Property>();
    }
    properties.add( new Property( key, value ) );
  }
  
  
  /**
   * Get property
   * 
   * @param key Key
   * @return Property or null if it does not exist
   * 
   */
  public String getProperty( String key ) {
    if( properties != null ) {
      for( Property property : properties ) {
        if( property.getKey().equals( key ) ) {
          return property.getValue();
        }
      }
      return null;
    }
    return null;
  }


  /**
   * Index of this row in the original order. This optional number will
   * be used when rows have equal values (equality) or equal sort values (comparable).
   * 
   * @return Optional: index of this row in the original order
   * 
   */
  public int getOriginalIndex() {
    return originalIndex == null ? 0 : originalIndex;
  }
  
  
  /**
   * Setter
   * 
   * @param Index of this row in the original order. This optional number will
   * be used when rows have equal values (equality) or equal sort values (comparable).
   * 
   */
  public void setOriginalIndex( Integer originalIndex ) {
    this.originalIndex = originalIndex;
  }


  @Override
  public Iterator<Cell> iterator() {
    return cells.iterator();
  }


}


