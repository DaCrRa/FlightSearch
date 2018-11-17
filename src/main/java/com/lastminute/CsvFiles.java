package com.lastminute;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

public final class CsvFiles
{
  private CsvFiles()
  {
  }

  public static List<List<String>> readAllRecords(String fileName)
  {
    try (Stream<List<String>> records = records(fileName))
    {
      return records.collect(toList());
    }
  }

  public static Stream<List<String>> records(String fileName)
  {
    return splitByCommaIgnoringHeader(linesOf(fileName));
  }

  private static Stream<List<String>> splitByCommaIgnoringHeader(Stream<String> lines)
  {
    return lines.skip(1)
                .map(line -> line.split(","))
                .map(Arrays::asList);
  }

  private static Stream<String> linesOf(String fileName)
  {
    try
    {
      return Files.lines(Paths.get(fileName));
    }
    catch (IOException e)
    {
      throw new UncheckedIOException("IO error accessing the CSV file " + fileName, e);
    }
  }
}
