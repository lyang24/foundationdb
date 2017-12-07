/*
 * FutureVoid.java
 *
 * This source file is part of the FoundationDB open source project
 *
 * Copyright 2013-2018 Apple Inc. and the FoundationDB project authors
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.apple.foundationdb;

import java.util.concurrent.Executor;

class FutureVoid extends NativeFuture<Void> {
	FutureVoid(long cPtr, Executor executor) {
		super(cPtr);
		registerMarshalCallback(executor);
	}

	@Override
	protected Void getIfDone_internal(long cPtr) throws FDBException {
		// With "future-cleanup" we get rid of FutureVoid_get and replace instead
		//  with a get on the error and throw if the error is not success.
		FDBException err = Future_getError(cPtr);

		if(!err.isSuccess()) {
			throw err;
		}
		return null;
	}
}
