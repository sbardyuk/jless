/**
 * Copyright 2010 Bazaarvoice, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * @author J. Ryan Stinnett (ryan.stinnett@bazaarvoice.com)
 */

package com.bazaarvoice.jless;

import org.testng.annotations.Test;

@Test
public class CompressedDiffTest extends DiffTest {

    @Override
    protected void setProcessorOptions() {
        PROCESSOR.setTranslationEnabled(true);
        PROCESSOR.setCompressionEnabled(true);
    }

    @Override
    protected String getGeneratedDirName() {
        return "compressed";
    }
}
