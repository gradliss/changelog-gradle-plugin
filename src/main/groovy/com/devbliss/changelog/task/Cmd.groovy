package com.devbliss.changelog.task

/**
 * @author Christian Soth <christian.soth@devbliss.com>
 *
 */

class Cmd {
  public static final RED = "\033[31m"
  public static final RED_BOLD = "\033[1;31m"

  public static final WHITE = "\033[37m"
  public static final WHITE_BOLD = "\033[1;37m"

  public static final NORMAL = "\033[22m" //remove bold

  public static final NEWLINE = System.getProperty("line.separator")
}
