package services.importer;

import java.io.IOException;
import utils.Separator;

/**
 * Functional Interface for parser implementations.
 *
 * @param <I> input.
 * @param <O> output.
 */
public interface Parser <I, O> {

  O parse(I input, Separator separator) throws IOException;
}
