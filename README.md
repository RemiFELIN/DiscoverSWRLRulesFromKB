# DiscoverSWRLRulesFromKB

## Contents

* [code/](https://github.com/RemiFELIN/DiscoverSWRLRulesFromKB/tree/main/code) 
> contains all Maven modules used in this project.

* [dataset/](https://github.com/RemiFELIN/DiscoverSWRLRulesFromKB/tree/main/dataset) 
> contains all dataset needed to execute commands described on **Demo** part

* [jar/](https://github.com/RemiFELIN/DiscoverSWRLRulesFromKB/tree/main/jar)
> contains the two jar files generated by the source code

* [doc/](https://github.com/RemiFELIN/DiscoverSWRLRulesFromKB/tree/main/doc)
it contains :
> the thesis of Duc Minh Tran about this project and correspond at [DiscoverSWRLRulesFromOntology](https://github.com/RemiFELIN/DiscoverSWRLRulesFromKB/tree/main/code/SWRLRulesFromOntology) part.

> the report of [Valeria Bellusci](https://github.com/ValeBellu) about the second version of this project which correspond at [DiscoverSWRLRulesFromRDF](https://github.com/RemiFELIN/DiscoverSWRLRulesFromKB/tree/main/code/SWRLRulesFromRDF) part. Valeria's original version is avalaible [here](https://github.com/ValeBellu/DiscoverSWRLRulesFromKB)

## Dependencies

### For users
> [Java](https://www.java.com/fr/download/) is required to run the jar files

### For contributors
> [Maven](https://maven.apache.org/download.cgi) is required to build the project after a modification of the source code, which allows to create the new jar files.

> [Java](https://www.java.com/fr/download/) is required to run the jar files

## Installation

To create new jar files from the source code, you must execute the following command : 

	mvn clean install

This command will compile source code, encapsulate all dependencies of each tools and create the following jar files on the root directory :

	DiscoverSWRLRulesFromOntology.jar
	DiscoverSWRLRulesFromRDF.jar

## DiscoverSWRLRulesFromRDF

	DiscoverSWRLRulesFromRDF.jar

### Description
It is an evolution of the SWRLRulesFromOntology. The classes, the properties and the individuals are taken directly from the RDF graphs and not from a file *.owl. It is necessary to query the SPARQL Endpoint, without using the OWL API.

The results are the same of the other algorithm, but they are constantly updated, since it takes data directly from the graphs, without having to download them. After discovering the new rules, the consistency check is done in the algorithm itself: it does other queries to understand if the rules are consistent with the RDF graphs. If the answer is yes, the rule will be added to the output file, otherwise it will be deleted.

**Input** 
> file *.txt with the named graphs to query (if any).

**Output** on *./SWRLRulesFromRDF/[Name of input file]/[ddMMyyyy_hhmmss]/* folder
> files *.txt with the rules, log traces, results of queries, ...

### Parameters
This jar takes the following parameters :

	usage: com.i3s.app.SWRLRulesRDF
	 -crrt,--crossover-rate <arg>       the population rate used to compute
	                                    the crossover size. (by default: 0.25)
	 -file <arg>                        path (relative or absolute) of file to test (*.txt for
	                                    DiscoverSWRLRulesFromRDF, *.owl for
	                                    DiscoverSWRLRulesFromOntology)
	 -mtrt,--mutation-rate <arg>        the population rate used to compute
	                                    the mutation size. (by default: 0.25)
	 -mtth,--mutation-threshold <arg>   the threshold thanks to which the
	                                    specialization or the generalization
	                                    is made. (by default: 0.2)
	 -nexe,--n-execution <arg>          the number of executions. (by default:
	                                    2)
	 -ngen,--n-generation <arg>         the number of generations. (by
	                                    default: 200)
	 -posz,--pop-size <arg>             the maximum number of patterns in a
	                                    population. (by default: 2000)
	 -spqu,--sparql-endpoint <arg>      SPARQL endpoint to make our requests
	                                    (ex:
	                                    http://covidontheweb.inria.fr/sparql)

**-file** and **-spqu** are required to execute tool, all others parameters are optionnals and have a default value.

## DiscoversSWRLRulesFromOntology

	DiscoverSWRLRulesFromOntology.jar

### Description
Thanks to the use of genetic alghorithms and the OWL API, it is possible to extract information from the ontology loaded. These new knowledge is in the form of SWRL rules. The genetic algorithm evolves the population containing the classes and the properties of the ontology, discovering new patterns, until the best remain. The rules are in a file *.txt .

**Input** 
> file *.owl (ontology)

**Output** on ./SWRLRulesFromOntology/[Name of input file]/[ddMMyyyy_hhmmss]/ folder
> files *.txt with the rules, log traces, consistent rules if checkConsistency was used


#### CheckConsistencyRules (Optional)

This is the algorithm used to check if the rules generated from the algorithm above are consistent with the ontology. It uses 10 threads to test 10 rules at the same time.

If *-cstf* parameters is given, the process is running automatically

### Parameters
This jar file takes the following parameters :

	usage: com.i3s.app.SWRLRulesOntology
	 -crrt,--crossover-rate <arg>           the population rate used to
	                                        compute the crossover size. (by
	                                        default: 0.25)
	 -cstf,--check-consistence-file <arg>   path (relative or absolute) of the full ontology
	                                        to be loaded. (*.owl file)
	 -file <arg>                            path (relative or absolute) of file to test (*.txt for
	                                        DiscoverSWRLRulesFromRDF, *.owl for
	                                        DiscoverSWRLRulesFromOntology)
	 -mtrt,--mutation-rate <arg>            the population rate used to
	                                        compute the mutation size. (by
	                                        default: 0.25)
	 -mtth,--mutation-threshold <arg>       the threshold thanks to which the
	                                        specialization or the
	                                        generalization is made. (by
	                                        default: 0.2)
	 -nexe,--n-execution <arg>              the number of executions. (by
	                                        default: 2)
	 -ngen,--n-generation <arg>             the number of generations. (by
	                                        default: 200)
	 -pkbu,--prefix-knowledge-base <arg>    Prefix URL of the knowledge base
	                                        to be analyze (ex:
	                                        http://www.biopax.org/examples/gly
	                                        colysis#)
	 -posz,--pop-size <arg>                 the maximum number of patterns in
	                                        a population. (by default: 2000)

**-file** and **-pkbu** are required to execute tool, all others parameters are optionnals and have a default value.

## Examples

### DiscoverSWRLRulesFromRDF.jar
> java -jar DiscoverSWRLRulesFromRDF.jar -file D:\Travail\MIAGE\Stage\repo\Maven\Covid19.txt -url http://covidontheweb.inria.fr/sparql -nexe 1 -ngen 10 -crrt 0.1 -mtrt 0.1 -posz 1000 -mtth 0.2

> java -jar DiscoverSWRLRulesFromRDF.jar -file Covid19.txt -url http://covidontheweb.inria.fr/sparql -nexe 3 -ngen 10

> java -jar DiscoverSWRLRulesFromRDF.jar -url http://covidontheweb.inria.fr/sparql -nexe 5 -ngen 20 -mtsz 400 -mtth 0.25 -file Covid19.txt

### DiscoverSWRLRulesFromOntology.jar
> java -jar DiscoverSWRLRulesFromOntology.jar -file Biopax_30.owl -pkbu http://www.biopax.org/examples/glycolysis# -nexe 2 -ngen 100 -crrt 0.3 -mtrt 0.1 -mtth 0.2

> java -jar DiscoverSWRLRulesFromOntology.jar -file Biopax_30.owl -pkbu http://www.biopax.org/examples/glycolysis# -nexe 1 -ngen 50 -mtth 0.25 -cstf Biopax_Full.owl

> java -jar DiscoverSWRLRulesFromOntology.jar -file Biopax_30.owl -pkbu http://www.biopax.org/examples/glycolysis# -cstf Biopax_Full.owl


## Example of use for studies

**Does the crossover rate greatly influence the number of consistent rules found?**

> java -jar DiscoverSWRLRulesFromRDF.jar -file D:\Travail\MIAGE\Stage\repo\Maven\Covid19.txt -url http://covidontheweb.inria.fr/sparql -nexe 1 -ngen 100 -crrt 0.1

> java -jar DiscoverSWRLRulesFromRDF.jar -file D:\Travail\MIAGE\Stage\repo\Maven\Covid19.txt -url http://covidontheweb.inria.fr/sparql -nexe 1 -ngen 100 -crrt 0.15

> java -jar DiscoverSWRLRulesFromRDF.jar -file D:\Travail\MIAGE\Stage\repo\Maven
\Covid19.txt -url http://covidontheweb.inria.fr/sparql -nexe 1 -ngen 100 -crrt 0.2

> java -jar DiscoverSWRLRulesFromRDF.jar -file D:\Travail\MIAGE\Stage\repo\Maven\Covid19.txt -url http://covidontheweb.inria.fr/sparql -nexe 1 -ngen 100 -crrt 0.25

> java -jar DiscoverSWRLRulesFromRDF.jar -file D:\Travail\MIAGE\Stage\repo\Maven\Covid19.txt -url http://covidontheweb.inria.fr/sparql -nexe 1 -ngen 100 -crrt 0.3

**... and the mutation rate?**

> java -jar DiscoverSWRLRulesFromRDF.jar -file D:\Travail\MIAGE\Stage\repo\Maven\Covid19.txt -url http://covidontheweb.inria.fr/sparql -nexe 1 -ngen 100 -mtrt 0.1

> java -jar DiscoverSWRLRulesFromRDF.jar -file D:\Travail\MIAGE\Stage\repo\Maven\Covid19.txt -url http://covidontheweb.inria.fr/sparql -nexe 1 -ngen 100 -mtrt 0.15

> java -jar DiscoverSWRLRulesFromRDF.jar -file D:\Travail\MIAGE\Stage\repo\Maven\Covid19.txt -url http://covidontheweb.inria.fr/sparql -nexe 1 -ngen 100 -mtrt 0.2

> java -jar DiscoverSWRLRulesFromRDF.jar -file D:\Travail\MIAGE\Stage\repo\Maven\Covid19.txt -url http://covidontheweb.inria.fr/sparql -nexe 1 -ngen 100 -mtrt 0.25

> java -jar DiscoverSWRLRulesFromRDF.jar -file D:\Travail\MIAGE\Stage\repo\Maven\Covid19.txt -url http://covidontheweb.inria.fr/sparql -nexe 1 -ngen 100 -mtrt 0.3
