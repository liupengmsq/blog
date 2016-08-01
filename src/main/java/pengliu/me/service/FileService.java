package pengliu.me.service;

import java.util.List;

/**
 * Created by peng on 16-4-27.
 */
public interface FileService
{
    List<String> getAllImageNamesFromServer(String basePath);
}
