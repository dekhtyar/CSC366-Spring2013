Team GLADE

Final set of deliverables can be found in the "final" folder

Team Members:

Adin Miller 	(amille35@calpoly.edu)

Elizabeth Loui 	(eloui@calpoly.edu)

George Reyes 	(greyes01@calpoly.edu)

Lauren Thurston 	(lathurst@calpoly.edu)

Diego Torres 	(dtorre08@calpoly.edu)


How to commit to git from your home computer:

The first thing you need to do is add your ssh key to github.

The first thing you need to do is add your SSH key to github.

The following site was help to me for macosx: https://help.github.com/articles/generating-ssh-keys

Then you can download the files from our branch from using the command below.

	git clone --branch GLADE git@github.com:dekhtyar/CSC366-Spring2013.git

this allows you to copy all the files from the branch GLADE.

If you add a new file you will need to do a git add command of the format
below.

	git add <file name>

That will add the file to the repository

In order to commit to your local branch you can use a git commit command.
Two important flags are -m(allows you to add a message to the commit) and
 -a(tells git to commit all changes to the repository).

	git commit -m -a 

Then to push the files to the database with the below command
	
	git push


