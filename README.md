- # Context-Aware-Name-Recommendation-for-Field-Renaming

  - [General Introduction](#General-Introduction)
  - [Contents of the Replication Package](#Contents-of-the-Replication-Package)
  - [Requirements](#Requirements)
  - [Data](#Data)
  - [How to Replicate the Evaluation?](#How-to-Replicate-the-Evaluation)

  # General Introduction

  This is the replication package for ICSE2024 submission, containing both tool and data that are requested by the replication. It also provides detailed instructions to replicate the evaluation.

  # Contents of the Replication Package

  /Dataset: Benchmark datasets for evaluation experiments.

  /IncoderTest: The implementation of the evaluated Incoder model.

  /Intellij_IDEATest: The implementation of the evaluated Intellij_IDEATest.

  /CARERTest: The implementation of the evaluated our proposed approach(CARER).
  
  # Requirements

  - Java 17.0.7 or newer
  - Pyhton 3.9.0 or newer, 'tokenizers>=0.12'

  # How to Replicate the Evaluation?

   ## Incoder Model
   1. **Clone replicate package to your local file system**

      https://github.com/anonymizez/Context-Aware-Name-Recommendation-for-Field-Renaming.git

  2. **Import project**

     Go to *File* -> *Open...*(Pycharm, download incoder: https://github.com/dpfried/incoder)

     Browse to the "IncoderTest" directory

     Click *OK*

     The project will be built automatically.

   3. **Run the experiment**

      Right-click on the file and select *incoderInfillingScripts.py*(/IncoderTest/coder/incoderInfillingScripts.py).

  ## CARER

   1. **Import project**

      Go to *File* -> *import* ->*Existing Projects into Workspace*

      Browse to the "CARERTest" directory

      Click *OK*

      The project will be built automatically.
  
   2. **Run the experiment**

       Right-click on the file and select *CARERTest.java*.

   ## Intellij IDEA

   1. **Import project**

      Go to *File* -> *import* ->*Existing Projects into Workspace*

      Browse to the "Intellij_IDEATest" directory

      Click *OK*

      The project will be built automatically.
  
   2. **Run the experiment**

       Right-click on the file and select IDEATest.java*.


