package pl.kielce.tu.worldyouthday.pointofinterest.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import pl.kielce.tu.worldyouthday.file.File;
import pl.kielce.tu.worldyouthday.file.FileService;
import pl.kielce.tu.worldyouthday.file.FileType;
import pl.kielce.tu.worldyouthday.file.resource.NewFileResource;
import pl.kielce.tu.worldyouthday.pointofinterest.PointOfInterest;
import pl.kielce.tu.worldyouthday.pointofinterest.PointOfInterestRepository;
import pl.kielce.tu.worldyouthday.pointofinterest.converter.PointOfInterestToResourceConverter;
import pl.kielce.tu.worldyouthday.pointofinterest.resource.PointOfInterestResource;
import pl.kielce.tu.worldyouthday.pointofinterest.resource.RemovePointOfInterestImageResource;
import pl.kielce.tu.worldyouthday.pointofinterest.validator.NewPointOfInterestImageValidator;
import pl.kielce.tu.worldyouthday.pointofinterest.validator.RemovePointOfInterestImageValidator;
import pl.kielce.tu.worldyouthday.utils.exception.ValidationException;
import pl.kielce.tu.worldyouthday.version.VersionTable;
import pl.kielce.tu.worldyouthday.version.Versionable;

import java.util.Optional;

/**
 * Created by Łukasz Wesołowski on 18.05.2016.
 */

@Service
public class PointOfInterestImageService {
    @Autowired
    private FileService fileService;

    @Autowired
    private PointOfInterestRepository pointOfInterestRepository;

    @Autowired
    private NewPointOfInterestImageValidator newPointOfInterestImageValidator;

    @Autowired
    private RemovePointOfInterestImageValidator removePointOfInterestImageValidator;

    @Autowired
    private PointOfInterestToResourceConverter pointOfInterestToResourceConverter;


    @Transactional
    @Versionable(table = VersionTable.POI)
    public PointOfInterestResource addPointOfInterestImage(String poiId, MultipartFile file) throws ValidationException {
        newPointOfInterestImageValidator.validate(poiId, file);


        File image = fileService.addFile(NewFileResource.newBuilder()
                .withFile(file)
                .withType(FileType.POI_IMAGE)
                .build());

        PointOfInterest pointOfInterest = pointOfInterestRepository.findOne(poiId);
        pointOfInterest.addImage(image);

        return pointOfInterestToResourceConverter.convert(pointOfInterestRepository.saveAndFlush(pointOfInterest));


    }

    @Transactional
    @Versionable(table = VersionTable.POI)
    public PointOfInterestResource removePointOfInterestImage(RemovePointOfInterestImageResource resource) throws ValidationException {
        removePointOfInterestImageValidator.validate(resource);

        PointOfInterest pointOfInterest = pointOfInterestRepository.findOne(resource.getPointOfInterestId());
        Optional<File> imageToRemove = pointOfInterest.getImages().stream().filter(i -> i.getId().equals(resource.getImageId())).findFirst();

        pointOfInterest.removeImage(imageToRemove.get());
        fileService.removeFile(imageToRemove.get().getId());

        return pointOfInterestToResourceConverter.convert(pointOfInterestRepository.saveAndFlush(pointOfInterest));
    }

    public byte[] getPointOfInterestImageContent(String imageId) {
        Optional<File> file = Optional.ofNullable(fileService.findFile(imageId));
        if (file.isPresent() && file.get().getType().equals(FileType.POI_IMAGE)) {
            return file.get().getContent();
        } else {
            return new byte[0];
        }
    }
}
