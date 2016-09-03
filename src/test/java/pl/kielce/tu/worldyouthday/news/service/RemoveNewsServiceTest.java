package pl.kielce.tu.worldyouthday.news.service;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import pl.kielce.tu.worldyouthday.Application;
import pl.kielce.tu.worldyouthday.news.resource.NewsResource;
import pl.kielce.tu.worldyouthday.utils.exception.ValidationException;

/**
 * Created by Łukasz Wesołowski on 24.05.2016.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@ActiveProfiles("test")
public class RemoveNewsServiceTest extends AbstractTransactionalJUnit4SpringContextTests {
    @Autowired
    private RemoveNewsService removeNewsService;

    @Autowired
    private FindNewsService findNewsService;

    @Test
    public void shouldRemoveNews() throws ValidationException {
        removeNewsService.removeNews("530994c3-6ca0-4402-af96-48f134473b8f");

        NewsResource result = findNewsService.findNews("530994c3-6ca0-4402-af96-48f134473b8f");

        Assert.assertEquals(null, result);
    }

    @Test(expected = ValidationException.class)
    public void shouldNotRemoveNewsWithoutValidId() throws ValidationException {
        removeNewsService.removeNews("BLEE");
    }
}
