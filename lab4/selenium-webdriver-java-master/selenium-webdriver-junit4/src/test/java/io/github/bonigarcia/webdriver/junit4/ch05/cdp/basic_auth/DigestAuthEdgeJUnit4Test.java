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
package io.github.bonigarcia.webdriver.junit4.ch05.cdp.basic_auth;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.Duration;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.HasAuthentication;
import org.openqa.selenium.UsernameAndPassword;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import io.github.bonigarcia.wdm.WebDriverManager;

@Ignore("Since the online service does not accept many consecutive requests")
public class DigestAuthEdgeJUnit4Test {

    WebDriver driver;

    @Before
    public void setup() {
        driver = WebDriverManager.edgedriver().create();
    }

    @After
    public void teardown() throws InterruptedException {
        // FIXME: pause for manual browser inspection
        Thread.sleep(Duration.ofSeconds(3).toMillis());

        driver.quit();
    }

    @Test
    public void testDigestAuth() {
        ((HasAuthentication) driver)
                .register(() -> new UsernameAndPassword("guest", "guest"));

        driver.get("https://jigsaw.w3.org/HTTP/Digest/");

        WebElement body = driver.findElement(By.tagName("body"));
        assertThat(body.getText()).contains("Your browser made it!");
    }

}
