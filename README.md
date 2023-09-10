<h1 align="center">Sist-down</h1>

<b>Sist-down</b> is a <b>Download Manager</b> and <b>Proxy Application</b> that was created to solve issues of intranet network infrastructure. This program was created in one day to solve the internal network problems faced at DNIT (National Department of Infrastructure and Transport).

The engineering team was facing difficulties visualizing and evaluating the products (images and videos) stored on the servers due to a high flow of requests.

Sist-down solved this problem by allowing videos and images to be scheduled for download and stored on each engineer's local machine, following their workflow and schedule, and taking into account each machine's local storage capacity.


![how-it-works](https://github.com/victordalosto/sist-down/blob/master/documentation/assets/how-it-works.png?raw=true)

## Features
Sist-down has been revamped to include a range of new features, such as automatic system restart, context switching between local and network, and deployment on a shared machine to facilitate maintenance, among others. The code has been refactored, and the functionality presentation in the visual layer has been restructured. A local database has also been created to manage the available endpoints.



## How to use
![tela-inicial](https://github.com/victordalosto/sist-down/blob/master/documentation/assets/print-tela.png?raw=true)

To use Sist-down, the engineering team would open the application, which prompts a shell prompt. From there, they would choose the roads they intend to analyze throughout the week.

Once the roads are selected, Sist-down schedules the download of the corresponding videos from the database and stores them on the engineer's local machine. From that point on, the application works as a proxy, redirecting the calls from the original content to the locally stored content, which allows for faster and smoother analysis.

The application's easy-to-use interface and streamlined functionality make it a reliable tool for engineering teams looking to improve their workflow and productivity. With Sist-down, the engineering team can efficiently analyze road conditions and make informed decisions without worrying about server overload or download speed issues.
order66
