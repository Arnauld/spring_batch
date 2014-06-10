Feature: I want to retrieve files for LoanML batch from production location


Scenario: files are available in "in" folder

Given the loanML files pattern is "nuooorgggg" 
# "CPMPROD_D_FSI_LOB.*_C.(XML|xml)|CPMPROD_D_FSI_LOB.*_E.(XML|xml)"
Given the remote "in" folder contains the following files: 
| CPMPROD_D_FSI_LOB3of4_20140609_0032_C.xml |
| CPMPROD_D_FSI_LOB3of4_20140211_0032_C.xml |
| CPMPROD_D_FSI_LOB3of4_20140211_0033_E.xml |
| CPMPROD_D_CHL_SWPRATE_20140609_0013_C.xml |
| HEDGEPROD_D_FIC_BBO_20140609_0009_C.xml   |     
When I launch the fetchFiles batch for loanML and for date 2014-FEB-11
Then the following files should become available in local "in" folder:
| CPMPROD_D_FSI_LOB3of4_20140211_0032_C.xml |
| CPMPROD_D_FSI_LOB3of4_20140211_0033_E.xml |















