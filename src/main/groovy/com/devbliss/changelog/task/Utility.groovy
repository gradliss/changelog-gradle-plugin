package com.devbliss.changelog.task

/**
 * @author Christian Soth <christian.soth@devbliss.com>
 *
 */

class Utility {
  //Some static color's for cmd
  public static final RED = "\033[31m"
  public static final RED_BOLD = "\033[1;31m"

  public static final WHITE = "\033[37m"
  public static final WHITE_BOLD = "\033[1;37m"

  public static final NORMAL = "\033[22m" //remove bold

  public static final NEWLINE = System.getProperty("line.separator")

  //Regex for changelog manipulation
  public static final regexVersionWithoutSuffix = /(\d)*(\.\d*).*/
  public static final regexVersionWithSuffix = /(\d)*(\.\d*)+(-).*/
  public static final regexVersionNumber = /(\d)*(\.\d*){0,2}/
  public static final regexText = /(- \[(.*)\])(.*?).*/
  public static final regexChangeNameDate = /\-- Last change from:(.*?)(\d+).*/
}
