package service;

import java.io.File;
import java.util.List;

public interface Counter {

    default long count(List<File> fileList) {
        return 0;
    }
}
