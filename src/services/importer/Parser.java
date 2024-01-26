package services.importer;

import java.io.IOException;
import utils.Separator;

public interface Parser <I, O> {

  O parse(I input, Separator separator) throws IOException;
}
