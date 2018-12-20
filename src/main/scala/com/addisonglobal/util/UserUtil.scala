package com.addisonglobal.util

import java.text.{DateFormat, SimpleDateFormat}
import java.util.{Date, TimeZone}

import com.addisonglobal.constants.UserConstants

object UserUtil {

  /*
   * This method produces the current time stamp with date pattern - yyyy-MM-dd'T'HH:mm:ssZ
   * and time zone as UTC and returns the same in String format
   */
  def getCurrentUtcTimestamp: String = {
    val df: DateFormat = new SimpleDateFormat(UserConstants.datePattern)
    df.setTimeZone(TimeZone.getTimeZone(UserConstants.timeZone))
    df.format(new Date(System.currentTimeMillis()))
  }

}
