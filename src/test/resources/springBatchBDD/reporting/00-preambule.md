
VERSION CONTROL
---------------

 Version | Description | Distribution | Date [dd/mm/yy] | Author(s)
---------|-------------|--------------|-----------------|-----------------
 0.1     |  Draft      | Draft        |  26/02/14       | Vincent Fuchs


PARTICIPANTS OF THIS DOCUMENT
-----------------------------

 Name             | Organization
------------------|----------------
  Vincent Fuchs   | ITEC/FCC/RPP
  Arnauld Loyer   | ITEC/COO/TUP


REVIEW AND APPROVAL
-------------------

Name          | Organization / Role   | Date [dd/mm/yy] |Comments
--------------|-----------------------|-----------------|------
    &nbsp;    | &nbsp;                |    &nbsp;       | &nbsp;


REFERENCE DOCUMENTS
-------------------

 Id  | Title                                                                          | Author(s)   | Version | Date [dd/mm/yy]
-----|--------------------------------------------------------------------------------|-------------|---------|-------------------
 [1] | [Spring Batch](http://docs.spring.io/spring-batch/trunk/reference/htmlsingle/) | &nbsp;      |   n/a   | n/a


FetchFiles, global overview
==========================================================

This module works like copy, but in reverse. It is used for fetching files
from remote machines and storing them locally in a file tree, organized by
hostname. Note that this module is written to transfer log files that might
not be present, so a missing remote file won’t be an error unless
`fail_on_missing` is set to `yes`.