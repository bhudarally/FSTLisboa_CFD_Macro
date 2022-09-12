% HOW TO IMPORT DATA:
% GO TO HOME -> IMPORT DATA -> SELECT RIDE HEIGHT DATA PROCESSING.XLSX
% SELECT FROM CELLS D5 TO J30 IN ORDER TO HAVE ALL THE DATA.

front_ride_height = RideHeightDataProcessing{:,1}';
rear_ride_height = RideHeightDataProcessing{:,2}';

cl = RideHeightDataProcessing{:,3}';
cd = RideHeightDataProcessing{:,4}';
cpf = RideHeightDataProcessing{:,5}';
%cpr = RideHeightDataProcessing{:,6}';
eff = RideHeightDataProcessing{:,7}';

type_of_interpolation = 'cubic';

[xi, yi] = meshgrid(20:0.01:60,30:0.01:72.5);

%cli = interpn(front_ride_height,rear_ride_height,cl,xi,yi,'cubic');

cli = griddata(front_ride_height,rear_ride_height,cl,xi,yi,type_of_interpolation);
cdi = griddata(front_ride_height,rear_ride_height,cd,xi,yi,type_of_interpolation);
cpfi = griddata(front_ride_height,rear_ride_height,cpf,xi,yi,type_of_interpolation);
effi = griddata(front_ride_height,rear_ride_height,eff,xi,yi,type_of_interpolation);

figure(1)
contourf(xi,yi,cli,50,'LineStyle','none');
colormap(jet);
xlabel('Front ride height [mm]');
ylabel('Rear ride height [mm]');
c = colorbar;
c.Label.String = 'CL';
caxis([3.25 4.25])
saveas(gcf,'CL.png');

figure(2)
contourf(xi,yi,cdi,50,'LineStyle','none');
colormap(jet);
xlabel('Front ride height [mm]');
ylabel('Rear ride height [mm]');
c = colorbar;
c.Label.String = 'CD';
saveas(gcf,'CD.png')

figure(3)
contourf(xi,yi,cpfi,50,'LineStyle','none');
colormap(jet);
xlabel('Front ride height [mm]');
ylabel('Rear ride height [mm]');
c = colorbar;
c.Label.String = 'Load Distribution';
saveas(gcf,'Loads.png')

figure(4)
contourf(xi,yi,effi,50,'LineStyle','none');
colormap(jet);
xlabel('Front ride height [mm]');
ylabel('Rear ride height [mm]');
c = colorbar;
c.Label.String = 'Efficiency';
saveas(gcf,'Eff.png')
