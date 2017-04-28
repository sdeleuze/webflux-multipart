Comment/uncomment either `MultipartController` / `MultipartRoute` depending if you want
to test the annotation-based variant or the functional one (you can't use both at the
same time).

Run `WebFluxMultipartApplication`.

Go to `http://localhost:8080/index.html`, fill a name and upload a file which should be
uploaded in the project directory.