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
package io.github.bonigarcia.webdriver.seljup.ch06.cloud_providers;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assumptions.assumeThat;

import java.net.MalformedURLException;
import java.net.URL;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;

import io.github.bonigarcia.seljup.DriverCapabilities;
import io.github.bonigarcia.seljup.DriverUrl;
import io.github.bonigarcia.seljup.SeleniumJupiter;

@ExtendWith(SeleniumJupiter.class)
class SauceLabsSelJupTest {

    @DriverUrl
    URL remoteUrl;

    @DriverCapabilities
    MutableCapabilities capabilities;

    @BeforeEach
    void setup() throws MalformedURLException {
        String username = System.getProperty("sauceLabsUsername");
        String accessKey = System.getProperty("sauceLabsAccessKey");

        // An alternative way to read username and key is using envs:
        // String username = System.getenv("SAUCELABS_USERNAME");
        // String accessKey = System.getenv("SAUCELABS_ACCESS_KEY");

        assumeThat(username).isNotEmpty();
        assumeThat(accessKey).isNotEmpty();

        capabilities = new MutableCapabilities();
        capabilities.setCapability("username", username);
        capabilities.setCapability("access_key", accessKey);
        capabilities.setCapability("name", "My SauceLabs test");
        capabilities.setCapability("browserVersion", "latest");

        ChromeOptions options = new ChromeOptions();
        options.setCapability("sauce:options", capabilities);
        remoteUrl = new URL(
                "https://ondemand.eu-central-1.saucelabs.com:443/wd/hub");
    }

    @Test
    void testSauceLabs(RemoteWebDriver driver) {
        driver.get("https://bonigarcia.dev/selenium-webdriver-java/");
        assertThat(driver.getTitle()).contains("Selenium WebDriver");
    }

}
