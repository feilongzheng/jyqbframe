/**
 * Copyright (C) 2015 Fernando Cejas Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.dfppm.giveu.base;

public abstract class BasePresenter<T extends IView> {
  boolean isDestroy = false;

  protected abstract void init(T view);
  protected void onStart(){

  }
  protected void onResume(){

  }
  protected void onPause(){

  }
  protected void onStop(){

  }
  protected void onDestroy(){
    isDestroy = true;
  }


}
