# eux-rina-irsync

Small app offering scheduled RINA IR SYNC (and a REST API with Swagger) using RINA SDK.

## main functionality

* sync IR
  
## known shortcomings

* this version assumes that it takes less than four minutes to request potentially new IR content from your AP, i.e. that your anti-
  malware does not take more than four minutes. If it does, don't worry, eux-rina-irsync will pick up the message on the next pass after
  20 minutes.
  
* The above mentioned four minutes cannot be configured, the 20 minutes can, see application.yml 
# IRSYNC scheduling properties
cron:
  syncRate: 1200000
  
* eux-rina-irsync needs the RINA CPI SDK patches from ResourcesApi.path in order to work. They are included in the source here at
  eu.ec.dgempl.eessi... and compiled into the fat jar if you use e.g. maven install.

* this is a scaled down branch of NAV's EUX RINA ADMIN tools to administer several things in RINA, like users/password, roles, NIE, etc.
  The full functionality is rather customized to NAV, and honestly we don't have the spare time to generalize it.
  
* currently, only one RINA can be addressed. Full EUX RINA ADMIN supports many RINAs called "tenants" in application.yml.
  IR SYNC only supports one, because of how we use admin username and password in our TEST and ACCEPTANCE environments. 
  
## TODOs

* Hm?
* Reintroduce support for multiple RINAs
* or
* remove the possibly complicated code and config for admin username and password.