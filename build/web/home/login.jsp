        <%
        /*    ArrayList cars = (ArrayList) request.getAttribute("cars");
            for (int i=0; i < cars.size(); i++) {
                out.println(cars.get(i));
            }

        //out.println(request.getContextPath());*/
        %>
<div class="bs-docs-section">
        <div class="row">
          <div class="col-lg-12">
            <div class="page-header ">
              <h2 class="headline">Login panel</h2>
            </div>
          </div>
        </div>

        <div class="row">
          <div class="col-lg-6">
            <div class="well" extra-class="bs-component">
              <form class="">
                  <div class="form-group">
                    <label class="control-label" for="inputEmail">Email</label>
                    <div class="">
                      <input type="text" placeholder="Email" id="inputEmail" class="form-control">
                    </div>
                  </div>
                  <div class="form-group">
                    <label class="control-label" for="inputPassword">Password</label>
                    <div class="">
                      <input type="password" placeholder="Password" id="inputPassword" class="form-control">
                      <div class="checkbox">
                        <label>
                          <input type="checkbox"> Remember me
                        </label>
                      </div>
                    </div>
                  </div>
                  <div class="form-group">
                    <div class="">
                      <button class="btn btn-primary btn-lg" type="submit">Submit</button>
                    </div>
                  </div>
               
              </form>
            </div>
          </div>
        </div>
      </div>