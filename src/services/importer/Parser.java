package services.importer;

import utils.Separator;

public interface Parser <I, O> {

  O parse(I input, Separator separator);
}
