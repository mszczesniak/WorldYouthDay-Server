package pl.kielce.tu.worldyouthday.file;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import pl.kielce.tu.worldyouthday.Application;
import pl.kielce.tu.worldyouthday.file.resource.NewFileResource;
import pl.kielce.tu.worldyouthday.utils.exception.ValidationException;

/**
 * Created by Łukasz Wesołowski on 11.05.2016.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@ActiveProfiles("test")
public class FileServiceTest extends AbstractTransactionalJUnit4SpringContextTests {
    @Autowired
    private FileService fileService;

    @Test
    public void shouldAddFile() throws Exception {
        MockMultipartFile fileMock = new MockMultipartFile("file", "zabytek.jpg", null, "Zawartość obrazka".getBytes());

        NewFileResource resource = NewFileResource.newBuilder()
                .withFile(fileMock)
                .withType(FileType.POI_IMAGE)
                .build();

        File file = fileService.addFile(resource);

        Assert.assertArrayEquals(fileMock.getBytes(), file.getContent());
        Assert.assertEquals(FileType.POI_IMAGE, file.getType());
        Assert.assertEquals(0L, file.getVersion().longValue());
    }

    @Test(expected = ValidationException.class)
    public void shouldNotAddFileWithoutFile() throws Exception {
        NewFileResource resource = NewFileResource.newBuilder()
                .withType(FileType.POI_IMAGE)
                .build();

        fileService.addFile(resource);
    }

    @Test(expected = ValidationException.class)
    public void shouldNotAddFileWithoutType() throws Exception {
        NewFileResource resource = NewFileResource.newBuilder()
                .withFile(new MockMultipartFile("file", "zabytek.jpg", null, "Zawartość obrazka".getBytes()))
                .build();

        fileService.addFile(resource);
    }

    @Test(expected = ValidationException.class)
    public void shouldNotAddFileWithWrongExtension() throws Exception {
        NewFileResource resource = NewFileResource.newBuilder()
                .withFile(new MockMultipartFile("file", "zabytek.exe", null, "Zawartość obrazka".getBytes()))
                .build();

        fileService.addFile(resource);
    }

    @Test
    public void shouldRemoveFile() throws Exception {
        NewFileResource resource = NewFileResource.newBuilder()
                .withFile(new MockMultipartFile("file", "zabytek.jpg", null, "Zawartość obrazka".getBytes()))
                .withType(FileType.POI_IMAGE)
                .build();

        File file = fileService.addFile(resource);

        fileService.removeFile(file.getId());

        Assert.assertEquals(null, fileService.findFile(file.getId()));
    }

    @Test(expected = ValidationException.class)
    public void shouldNotRemoveFileWithoutValidId() throws Exception {
        fileService.removeFile("BLEEEE");
    }
}
