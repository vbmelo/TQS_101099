/*
 * (C) Copyright 2021 Boni Garcia (https://bonigarcia.github.io/)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */
package io.github.bonigarcia.webdriver.seljup.ch05.bidi;

import static java.lang.invoke.MethodHandles.lookup;
import static org.assertj.core.api.Assertions.assertThat;
import static org.slf4j.LoggerFactory.getLogger;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.devtools.events.CdpEventTypes;
import org.openqa.selenium.devtools.events.DomMutationEvent;
import org.openqa.selenium.logging.HasLogEvents;
import org.slf4j.Logger;

import io.github.bonigarcia.seljup.SeleniumJupiter;

@ExtendWith(SeleniumJupiter.class)
class DomMutationChromeSelJupTest {

    static final Logger log = getLogger(lookup().lookupClass());

    @Test
    void testDomMutation(ChromeDriver driver) throws InterruptedException {
        driver.get("https://bonigarcia.dev/selenium-webdriver-java/");

        HasLogEvents logger = (HasLogEvents) driver;
        JavascriptExecutor js = (JavascriptExecutor) driver;

        AtomicReference<DomMutationEvent> seen = new AtomicReference<>();
        CountDownLatch latch = new CountDownLatch(1);
        logger.onLogEvent(CdpEventTypes.domMutation(mutation -> {
            seen.set(mutation);
            latch.countDown();
        }));

        WebElement img = driver.findElement(By.tagName("img"));
        String newSrc = "img/award.png";
        String script = String.format("arguments[0].src = '%s';", newSrc);
        js.executeScript(script, img);

        assertThat(latch.await(10, TimeUnit.SECONDS)).isTrue();
        assertThat(seen.get().getElement().getAttribute("src"))
                .endsWith(newSrc);
    }

}
