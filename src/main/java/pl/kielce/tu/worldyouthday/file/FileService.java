package pl.kielce.tu.worldyouthday.file;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.kielce.tu.worldyouthday.file.resource.NewFileResource;
import pl.kielce.tu.worldyouthday.file.validator.NewFileValidator;
import pl.kielce.tu.worldyouthday.utils.exception.ValidationException;

import java.io.IOException;
import java.util.UUID;

/**
 * Created by Łukasz Wesołowski on 10.05.2016.
 */

@Service
public class FileService {
    @Autowired
    private FileRepository fileRepository;

    @Autowired
    private NewFileValidator newFileValidator;

    @Transactional
    public File addFile(NewFileResource resource) throws ValidationException {
        newFileValidator.validate(resource);

        String fileId = UUID.randomUUID().toString();

        File file = null;

        try {
            file = new File(
                    fileId,
                    resource.getFile().getBytes(),
                    resource.getType(),
                    0L
            );
        } catch (IOException ex) {
            throw new ValidationException("Invalid file content", ex);
        }

        file = fileRepository.saveAndFlush(file);

        return file;
    }

    @Transactional
    public void removeFile(String id) throws ValidationException {
        File file = fileRepository.findOne(id);
        if (file == null) {
            throw new ValidationException(String.format("File with id: %s doesn't exists", id));
        }

        fileRepository.delete(file);
    }

    public File findFile(String id) {
        return fileRepository.findOne(id);
    }
}
