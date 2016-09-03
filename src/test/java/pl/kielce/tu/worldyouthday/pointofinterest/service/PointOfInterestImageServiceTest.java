package pl.kielce.tu.worldyouthday.pointofinterest.service;

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
import pl.kielce.tu.worldyouthday.pointofinterest.resource.PointOfInterestResource;
import pl.kielce.tu.worldyouthday.pointofinterest.resource.RemovePointOfInterestImageResource;
import pl.kielce.tu.worldyouthday.utils.exception.ValidationException;

/**
 * Created by Łukasz Wesołowski on 18.05.2016.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@ActiveProfiles("test")
public class PointOfInterestImageServiceTest extends AbstractTransactionalJUnit4SpringContextTests {
    @Autowired
    private PointOfInterestImageService pointOfInterestImageService;

    @Autowired
    private FindPointOfInterestService findPointOfInterestService;

    @Test
    public void shouldAddPointOfInterestImage() throws ValidationException {
        MockMultipartFile file = new MockMultipartFile("file", "zdjecie.jpg", null, "Zawartosc".getBytes());
        String pointOfInterestId = "b9288672-14d9-42cc-a855-f4f9f4d75ce2";

        pointOfInterestImageService.addPointOfInterestImage(pointOfInterestId, file);

        PointOfInterestResource result = findPointOfInterestService.findPointOfInterestInAllLanguages(pointOfInterestId);
        String imageId = result.getImagesId().get(0);
        byte[] imageContent = pointOfInterestImageService.getPointOfInterestImageContent(imageId);

        Assert.assertArrayEquals("Zawartosc".getBytes(), imageContent);
    }

    @Test(expected = ValidationException.class)
    public void shouldNotAddPointOfInterestImageWithoutValidPointOfInterestId() throws ValidationException {

        MockMultipartFile file = new MockMultipartFile("file", "zdjecie.jpg", null, "Zawartosc".getBytes());
        String pointOfInterestId = "BLEE";


        pointOfInterestImageService.addPointOfInterestImage(pointOfInterestId, file);
    }

    @Test(expected = ValidationException.class)
    public void shouldNotAddPointOfInterestImageWithoutPointOfInterestId() throws ValidationException {

        MockMultipartFile file = new MockMultipartFile("file", "zdjecie.jpg", null, "Zawartosc".getBytes());
        String pointOfInterestId = null;

        pointOfInterestImageService.addPointOfInterestImage(pointOfInterestId, file);
    }

    @Test(expected = ValidationException.class)
    public void shouldNotAddPointOfInterestImageWithoutFile() throws ValidationException {

        MockMultipartFile file = null;
        String pointOfInterestId = "b9288672-14d9-42cc-a855-f4f9f4d75ce2";

        pointOfInterestImageService.addPointOfInterestImage(pointOfInterestId, file);
    }

    @Test(expected = ValidationException.class)
    public void shouldNotAddPointOfInterestImageWithoutValidFileExtension() throws ValidationException {
        MockMultipartFile file = new MockMultipartFile("file", "zabytek.exe", null, "Zawartosc".getBytes());
        String pointOfInterestId = "b9288672-14d9-42cc-a855-f4f9f4d75ce2";
        pointOfInterestImageService.addPointOfInterestImage(pointOfInterestId, file);
    }

    @Test
    public void shouldRemovePointOfInterestImage() throws ValidationException {
        MockMultipartFile file = new MockMultipartFile("file", "zdjecie.jpg", null, "Zawartosc".getBytes());
        String pointOfInterestId = "b9288672-14d9-42cc-a855-f4f9f4d75ce2";

        pointOfInterestImageService.addPointOfInterestImage(pointOfInterestId, file);

        PointOfInterestResource result = findPointOfInterestService.findPointOfInterestInAllLanguages("b9288672-14d9-42cc-a855-f4f9f4d75ce2");
        String imageId = result.getImagesId().get(0);

        RemovePointOfInterestImageResource removeResource = RemovePointOfInterestImageResource.newBuilder()
                .withPointOfInterestId("b9288672-14d9-42cc-a855-f4f9f4d75ce2")
                .withImageId(imageId)
                .build();


        pointOfInterestImageService.removePointOfInterestImage(removeResource);

        PointOfInterestResource removeResult = findPointOfInterestService.findPointOfInterestInAllLanguages("b9288672-14d9-42cc-a855-f4f9f4d75ce2");

        Assert.assertEquals(0, removeResult.getImagesId().size());
    }

    @Test(expected = ValidationException.class)
    public void shouldNotRemovePointOfInterestImageWithoutValidImageId() throws ValidationException {
        MockMultipartFile file = new MockMultipartFile("file", "zdjecie.jpg", null, "Zawartosc".getBytes());
        String pointOfInterestId = "b9288672-14d9-42cc-a855-f4f9f4d75ce2";

        pointOfInterestImageService.addPointOfInterestImage(pointOfInterestId, file);

        RemovePointOfInterestImageResource removeResource = RemovePointOfInterestImageResource.newBuilder()
                .withPointOfInterestId("b9288672-14d9-42cc-a855-f4f9f4d75ce2")
                .withImageId("BLEE")
                .build();


        pointOfInterestImageService.removePointOfInterestImage(removeResource);
    }

    @Test(expected = ValidationException.class)
    public void shouldNotRemovePointOfInterestImageWithoutImageId() throws ValidationException {
        MockMultipartFile file = new MockMultipartFile("file", "zdjecie.jpg", null, "Zawartosc".getBytes());
        String pointOfInterestId = "b9288672-14d9-42cc-a855-f4f9f4d75ce2";

        pointOfInterestImageService.addPointOfInterestImage(pointOfInterestId, file);

        RemovePointOfInterestImageResource removeResource = RemovePointOfInterestImageResource.newBuilder()
                .withPointOfInterestId("b9288672-14d9-42cc-a855-f4f9f4d75ce2")
                .build();


        pointOfInterestImageService.removePointOfInterestImage(removeResource);
    }

    @Test(expected = ValidationException.class)
    public void shouldNotRemovePointOfInterestImageWithoutValidPointOfInterestId() throws ValidationException {
        MockMultipartFile file = new MockMultipartFile("file", "zdjecie.jpg", null, "Zawartosc".getBytes());
        String pointOfInterestId = "b9288672-14d9-42cc-a855-f4f9f4d75ce2";

        pointOfInterestImageService.addPointOfInterestImage(pointOfInterestId, file);

        PointOfInterestResource result = findPointOfInterestService.findPointOfInterestInAllLanguages("b9288672-14d9-42cc-a855-f4f9f4d75ce2");
        String imageId = result.getImagesId().get(0);

        RemovePointOfInterestImageResource removeResource = RemovePointOfInterestImageResource.newBuilder()
                .withPointOfInterestId("BLEE")
                .withImageId(imageId)
                .build();


        pointOfInterestImageService.removePointOfInterestImage(removeResource);
    }

    @Test(expected = ValidationException.class)
    public void shouldNotRemovePointOfInterestImageWithoutPointOfInterestId() throws ValidationException {
        MockMultipartFile file = new MockMultipartFile("file", "zdjecie.jpg", null, "Zawartosc".getBytes());
        String pointOfInterestId = "b9288672-14d9-42cc-a855-f4f9f4d75ce2";

        pointOfInterestImageService.addPointOfInterestImage(pointOfInterestId, file);

        PointOfInterestResource result = findPointOfInterestService.findPointOfInterestInAllLanguages("b9288672-14d9-42cc-a855-f4f9f4d75ce2");
        String imageId = result.getImagesId().get(0);

        RemovePointOfInterestImageResource removeResource = RemovePointOfInterestImageResource.newBuilder()
                .withImageId(imageId)
                .build();


        pointOfInterestImageService.removePointOfInterestImage(removeResource);
    }
}
