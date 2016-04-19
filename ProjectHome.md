This project is a set of Repository OSID Provider implementations, originally developed for the concept mapping tool VUE (Visual Understanding Environment) at Tufts University. They will work with and Repository OSID v2 Consumer.

Available Repository Provider OSIDs in this Project:

  * flickr
  * NY Times Article Search
  * Wikipedia
  * Pubmed

These can be used to perform Keyword searches against their respsective services and return results as OSID Assets. These can be used by any application which acts as an OSID consumer. More information on OSIDs is listed below. These OSID were developed by the VUE project team.


The Repository Open Service Interface Definition (OSID) is an O.K.I. specification which defines the storing and retrieving of digital content, referred to as Assets. OSIDs are programmatic interfaces which comprise a Service Oriented Architecture for designing and building reusable and interoperable software. Multiple repositories can be managed or searched through the use of OSID adapter patterns where underneath a single Repository OSID can exist multiple Repository OSIDs forming a federation of repositories, where each implementation may be using a distinct incompatible technology and the OSID integrates them. For more information visit http://www.okiproject.org. To see a good demonstration of an OSID consumer in use see http://vue.tufts.edu