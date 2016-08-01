package pengliu.me.service.impl;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pengliu.me.service.FileService;

import javax.servlet.ServletContext;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

/**
 * Created by peng on 16-4-27.
 */
@Service
public class FileServiceImpl extends BaseService implements FileService
{
    private Logger logger = Logger.getLogger(FileServiceImpl.class);

    public List<String> getAllImageNamesFromServer(String basePath)
    {
        File basePathDir = new File(basePath);
        File[] files = basePathDir.listFiles();
        if(files == null)
        {
            return new ArrayList<String>();
        }
        Arrays.sort(files, new Comparator<File>()
        {
            public int compare(File o1, File o2)
            {
                return o1.getName().compareTo(o2.getName());
            }
        });

        List<String> fileNames = new ArrayList<String>();
        for(File file: basePathDir.listFiles())
        {
            fileNames.add(file.getName());
        }

        return fileNames;
    }
}
