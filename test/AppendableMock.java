import java.io.IOException;

/**
 * Mock for Appendable input, used for testing IOExceptions.
 */
public class AppendableMock implements Appendable {
  @Override
  public Appendable append(CharSequence csq) throws IOException {
    throw new IOException();
  }

  @Override
  public Appendable append(CharSequence csq, int start, int end) throws IOException {
    throw new IOException();
  }

  @Override
  public Appendable append(char c) throws IOException {
    throw new IOException();
  }
}