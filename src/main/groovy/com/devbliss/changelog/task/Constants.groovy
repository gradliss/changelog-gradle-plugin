package com.devbliss.changelog.task

/**
 * The Utility class gives the plugin some static variables to make the code more readable.
 * Includes Regex to manipulate the changelog file and some colors for the Command-line.
 *
 * @author Christian Soth <christian.soth@devbliss.com>
 * @author Philipp Karstedt <philipp.karstedt@devbliss.com>
 *
 * @version 0.0.1
 */

class Constants {
  public static final DEFAULT_CHANGELOG_FILENAME = 'changelog.md'

  //Some static color's for cmd
  public static final RED = "\033[31m"
  public static final RED_BOLD = "\033[1;31m"

  public static final WHITE = "\033[37m"
  public static final WHITE_BOLD = "\033[1;37m"

  public static final NORMAL = "\033[22m" //remove bold
  public static final RESET_COLOR_AND_STYLE = "\033[0m" //standard color

  public static final NEWLINE = "\r\n"

  //Regex for changelog manipulation
  public static final regexVersionWithoutSuffix = /(\d)*(\.\d*).*/
  public static final regexVersionWithSuffix = /(\d)*(\.\d*)+(-).*/
  public static final regexVersionNumber = /(\d)*(\.\d*){0,2}/
  public static final regexText = /( - )(.*?).*/
  public static final regexChangeNameDate = /\-- Last change by:(.*?)(\d+).*/
}
